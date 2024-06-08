package com.example.business;
 
import com.example.repository.*;
import com.example.dto.*;
import com.example.model.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.util.GSonUtils;
import com.example.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class ControllerBusiness {

	@Autowired
	LoggerUtil log;

	@Autowired
    EventoRepository eventoRepository;

	public void addDataEvento(EventoEntity data) {
		eventoRepository.save(data);
	}

	public List<EventoEntity> getDataEvento() {
		List<EventoEntity> result = new ArrayList<EventoEntity>();
		eventoRepository.findAll().forEach((final EventoEntity r) -> result.add(r));
		return result;
	}
   
	@Autowired
    LugarRepository lugarRepository;

	public void addDataLugar(LugarEntity data) {
		lugarRepository.save(data);
	}

	public List<LugarEntity> getDataLugar() {
		List<LugarEntity> result = new ArrayList<LugarEntity>();
		lugarRepository.findAll().forEach((final LugarEntity r) -> result.add(r));
		return result;
	}
   
	@Autowired
    RegistroRepository registroRepository;

	public void addDataRegistro(RegistroEntity data) {
		registroRepository.save(data);
	}

	public List<RegistroEntity> getDataRegistro() {
		List<RegistroEntity> result = new ArrayList<RegistroEntity>();
		registroRepository.findAll().forEach((final RegistroEntity r) -> result.add(r));
		return result;
	}

}
