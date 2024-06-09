package com.example.business;
 
import com.example.repository.*;
import com.example.model.*;


import com.example.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
public class ControllerBusiness {

	private final LoggerUtil log;
    private final EventoRepository eventoRepository;
	private final LugarRepository lugarRepository;
	private final RegistroRepository registroRepository;

	@Autowired
	public ControllerBusiness(LoggerUtil log, EventoRepository eventoRepository, LugarRepository lugarRepository, RegistroRepository registroRepository) {
		this.log = log;
		this.eventoRepository = eventoRepository;
		this.lugarRepository = lugarRepository;
		this.registroRepository = registroRepository;
	}


	public void addDataEvento(EventoEntity data) {
		eventoRepository.save(data);
	}

	public List<EventoEntity> getDataEvento() {
		List<EventoEntity> result = new ArrayList<>();
		eventoRepository.findAll().forEach((final EventoEntity r) -> result.add(r));
		return result;
	}

	public void addDataLugar(LugarEntity data) {
		lugarRepository.save(data);
	}

	public List<LugarEntity> getDataLugar() {
		List<LugarEntity> result = new ArrayList<>();
        for (LugarEntity r : lugarRepository.findAll()) {
            result.add(r);
        }
        return result;
	}

	public void updateDataLugar(String id, LugarEntity lugarDetails) {
		LugarEntity lugar = lugarRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Lugar no encontrado"));

		lugar.setNombre(lugarDetails.getNombre());
		lugar.setCapacidad(lugarDetails.getCapacidad());
		lugarRepository.save(lugar);
	}

	public void addDataRegistro(RegistroEntity data, String eventoId, String lugarId) {
		// Obtener el evento y el lugar de la base de datos
		EventoEntity eventoEntity = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new RuntimeException("Evento no encontrado"));
		LugarEntity lugarEntity = lugarRepository.findById(lugarId)
				.orElseThrow(() -> new RuntimeException("Lugar no encontrado"));

		// Verificar disponibilidad del lugar y realizar el registro
		if (lugarEntity.getCapacidad() > 0) {
			// Restar 1 al cupo del lugar
			lugarEntity.setCapacidad(lugarEntity.getCapacidad() - 1);

			// Mapear el DTO a la entidad de Registro
			RegistroEntity registro = MapperUtil.map(data, RegistroEntity.class);

			// Establecer la relación con el evento y el lugar
			registro.setEvento(eventoEntity);
			registro.setLugar(lugarEntity);

			// Guardar el registro en la base de datos
			registroRepository.save(registro);

			// Actualizar la capacidad del lugar en la base de datos
			lugarRepository.save(lugarEntity);
		}
	}

	public List<RegistroEntity> getDataRegistro() {
		List<RegistroEntity> result = new ArrayList<>();
		registroRepository.findAll().forEach((final RegistroEntity r) -> result.add(r));
		return result;
	}

	public EventoEntity addLugaresToEvento(String eventoId, Set<String> lugarIds) {
		EventoEntity evento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new RuntimeException("Evento no encontrado"));

		Iterable<LugarEntity> lugaresIterable = lugarRepository.findAllById(lugarIds);
		Set<LugarEntity> lugares = StreamSupport.stream(lugaresIterable.spliterator(), false)
				.collect(Collectors.toSet());

		evento.setLugares(lugares);

		return eventoRepository.save(evento);
	}


}
