package com.example.api;


import com.example.util.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.business.ControllerBusiness;

import com.example.dto.*;
import com.example.model.*;
import java.util.List;
import java.util.Set;

@RestController
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Accion exitosa"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@Api(value = "app-eventos-corporativos-api ApiController", description = "Eventos Corporativos es un app que se encarga de verificar diponilibades de lugares y permitir la creación de eventos en ellos. Adicional debe permiitr el registro de personas al evento",  tags = {"",""})
public class ApiController {

    private final ControllerBusiness controllerBusiness;

    @Autowired
    public ApiController(ControllerBusiness controllerBusiness) {
        this.controllerBusiness = controllerBusiness;
    }

	@Value("${spring.application.version}")
	private String version;

	@GetMapping("version")
    public ResponseEntity<String> version() {
        return new ResponseEntity(version, HttpStatus.OK);
    }




	@ApiOperation(value = "Obtener lista de Evento", notes = "Retorna listado de Evento del sistema xxxxx")
	@GetMapping("evento")
    public List<EventoResponseDTO> getEvento() {
       return MapperUtil.mapAll(controllerBusiness.getDataEvento(),EventoResponseDTO.class);
    }

	@PostMapping("evento")
    public ResponseEntity<EventoRequestDTO> putEvento(@RequestBody EventoRequestDTO dto) {
		
		EventoEntity data = MapperUtil.map(dto,EventoEntity.class);
	    controllerBusiness.addDataEvento(data);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

	@ApiOperation(value = "Obtener lista de Lugar", notes = "Retorna listado de Lugar del sistema xxxxx")
	@GetMapping("lugar")
    public List<LugarResponseDTO> getLugar() {
       return MapperUtil.mapAll(controllerBusiness.getDataLugar(),LugarResponseDTO.class);
    }

	@PostMapping("lugar")
    public ResponseEntity<LugarRequestDTO> putLugar(@RequestBody LugarRequestDTO dto) {
		
		LugarEntity data = MapperUtil.map(dto,LugarEntity.class);
	    controllerBusiness.addDataLugar(data);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PutMapping("/lugar/{id}")
    public ResponseEntity<LugarRequestDTO> updateLugar(@PathVariable String id, @RequestBody LugarRequestDTO lugarDetails) {
        LugarEntity data = MapperUtil.map(lugarDetails,LugarEntity.class);
        controllerBusiness.updateDataLugar(id, data);
        return new ResponseEntity(lugarDetails, HttpStatus.OK);
    }

	@ApiOperation(value = "Obtener lista de Registro", notes = "Retorna listado de Registro del sistema xxxxx")
	@GetMapping("registro")
    public List<RegistroResponseDTO> getRegistro() {
       return MapperUtil.mapAll(controllerBusiness.getDataRegistro(),RegistroResponseDTO.class);
    }

	@PostMapping("registro")
    public ResponseEntity<RegistroRequestDTO> putRegistro(@RequestBody RegistroRequestDTO dto, @RequestParam String eventoId, @RequestParam String lugarId) {

		RegistroEntity data = MapperUtil.map(dto,RegistroEntity.class);
	    controllerBusiness.addDataRegistro(data, eventoId, lugarId);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PostMapping("/evento/{eventoId}/lugar")
    public ResponseEntity<EventoEntity> addLugaresToEvento(@PathVariable String eventoId, @RequestBody Set<String> lugarIds) {
        EventoEntity updatedEvento = controllerBusiness.addLugaresToEvento(eventoId, lugarIds);
        return new ResponseEntity<>(updatedEvento, HttpStatus.OK);
    }


}
