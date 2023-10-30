package com.charapadev.cakenviewapi.modules.cakes;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.charapadev.cakenviewapi.exceptions.RestError;
import com.charapadev.cakenviewapi.modules.cakes.dtos.CakesPage;
import com.charapadev.cakenviewapi.modules.cakes.dtos.CreateCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.dtos.ShowCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.dtos.ShowDailyCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.entities.DailyCake;
import com.charapadev.cakenviewapi.modules.cakes.services.CakeService;
import com.charapadev.cakenviewapi.modules.cakes.services.DailyCakeService;
import com.charapadev.utils.PageUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Bolos", description = "Bolos e tortas disponíveis para descoberta e avaliação para os usuários")
@RequestMapping(value = "/cakes")
@RestController
@AllArgsConstructor
public class CakeController {

    private CakeService cakeService;
    private DailyCakeService dailyCakeService;
    private CakeMapper cakeMapper;

    @Operation(summary = "Lista bolos e tortas")
    @Parameter(name = "bestRated", description = "Lista os bolos pelos melhores avaliados de forma decrescente")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "Página contendo bolos cadastrados",
            content = @Content(schema = @Schema(implementation = CakesPage.class))
        )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Page<ShowCakeDTO> list(
        @Parameter(description = "Nome do bolo") @RequestParam(name = "name", required = false, defaultValue = "") String name,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "bestRated", required = false, defaultValue = "true") Boolean bestRated
    ) {
        Direction direction = bestRated ? Direction.DESC : Direction.ASC;

        PageRequest pageable = PageRequest.of(
            page, PageUtils.PER_PAGE,
            Sort.by("id").and(Sort.by(direction, "rating.average"))
        );
        Page<Cake> cakesFound = cakeService.list(pageable, name);

        return cakesFound.map(cakeMapper::toShow);
    }

    @Operation(summary = "Busca o bolo sorteado do dia")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "Bolo do dia encontrado",
            content = @Content(schema = @Schema(implementation = ShowDailyCakeDTO.class))
        )
    })
    @GetMapping(value = "/daily", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ShowDailyCakeDTO getCakeOfDay() {
        DailyCake dailyCake = dailyCakeService.getCurrent();

        return cakeMapper.toShowDaily(dailyCake);
    }

    @Operation(summary = "Lista os bolos em destaque")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "Bolos em destaque encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowCakeDTO.class), maxItems = 3))
        )
    })
    @GetMapping(value = "/trendings", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<ShowCakeDTO> listTrending() {
        return cakeService.listTrending().stream()
            .map(cakeMapper::toShow).toList();
    }

    @Operation(summary = "Cadastra um bolo")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", description = "Bolo cadastrado com sucesso",
            content = @Content(schema = @Schema(implementation = ShowCakeDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", description = "Erro na validação do corpo da requisição",
            content = @Content(schema = @Schema(implementation = RestError.class))
        )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody ShowCakeDTO create(@RequestBody @Valid CreateCakeDTO createDTO) {
        Cake newCake = cakeService.create(createDTO);

        return cakeMapper.toShow(newCake);
    }

    @Operation(summary = "Busca um bolo")
    @Parameter(name = "id", description = "Identificador do bolo")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "Bolo encontrado",
            content = @Content(schema = @Schema(implementation = ShowCakeDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", description = "Bolo não encontrado pelo identificador especificado",
            content = @Content(schema = @Schema(implementation = RestError.class))
        )
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ShowCakeDTO find(@PathVariable("id") Long cakeId) {
        Cake cakeFound = cakeService.find(cakeId);

        return cakeMapper.toShow(cakeFound);
    }

    @Operation(summary = "Exclui um bolo")
    @Parameter(name = "id", description = "Identificador do bolo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Bolo removido som sucesso"),
        @ApiResponse(
            responseCode = "404", description = "Bolo não encontrado pelo identificador especificado",
            content = @Content(schema = @Schema(implementation = RestError.class))
        )
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") Long cakeId) {
        cakeService.remove(cakeId);
    }

}
