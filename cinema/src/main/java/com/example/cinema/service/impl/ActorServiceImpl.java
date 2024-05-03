package com.example.cinema.service.impl;

import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.ActorMapper;
import com.example.cinema.model.Actor;
import com.example.cinema.repository.ActorRepository;
import com.example.cinema.service.ActorService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private static final String CANNOT_FIND_ACTOR_BY_ID_MSG = "Cannot find actor by Id: ";
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Override
    public Page<ActorResponseDto> getAll(Pageable pageable) {
        return actorRepository.findAll(pageable).map(actorMapper::toDto);
    }

    @Override
    public ActorResponseDto findById(Long id) {
        Actor actorFromDb = actorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CANNOT_FIND_ACTOR_BY_ID_MSG + id)
        );
        return actorMapper.toDto(actorFromDb);
    }

    @Override
    public ActorResponseDto saveActor(ActorCreateDto createDto) {
        Actor actor = actorMapper.toModel(createDto);
        actorRepository.save(actor);
        return actorMapper.toDto(actor);
    }

    @Override
    public ActorResponseDto updateById(Long id, ActorUpdateDto updateDto) {
        Actor actorFromDb = actorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CANNOT_FIND_ACTOR_BY_ID_MSG + id)
        );
        setUpdatedFields(actorFromDb, updateDto);
        actorRepository.save(actorFromDb);
        return actorMapper.toDto(actorFromDb);
    }

    @Override
    public void deleteById(Long id) {
        actorRepository.deleteById(id);
    }

    @Override
    public Page<ActorResponseDto> searchByName(String name, Pageable pageable) {
        return actorRepository.findByNameStartingWithIgnoreCase(name, pageable)
                .map(actorMapper::toDto);
    }

    private void setUpdatedFields(Actor actorFromDb, ActorUpdateDto updateDto) {
        Optional.ofNullable(updateDto.getAge()).ifPresent(actorFromDb::setAge);
        Optional.ofNullable(updateDto.getDescription()).ifPresent(actorFromDb::setDescription);
        Optional.ofNullable(updateDto.getName()).ifPresent(actorFromDb::setName);
    }
}
