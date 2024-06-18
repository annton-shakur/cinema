package com.example.cinema.service.impl;

import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.ActorMapper;
import com.example.cinema.model.Actor;
import com.example.cinema.repository.ActorRepository;
import com.example.cinema.service.ActorService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private static final String CANNOT_FIND_ACTOR_BY_ID_MSG = "Cannot find actor by Id: ";
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;
    private final Logger logger;

    @Override
    public Page<ActorResponseDto> getAll(final Pageable pageable) {
        logger.info("[Service]: Getting all actors");
        return actorRepository.findAll(pageable).map(actorMapper::toDto);
    }

    @Override
    public ActorResponseDto findById(final Long id) {
        logger.info("[Service]: Finding actor by id: {}", id);
        Actor actorFromDb = actorRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_ACTOR_BY_ID_MSG + "{}", id);
                    return new EntityNotFoundException(CANNOT_FIND_ACTOR_BY_ID_MSG + id);
                });
        return actorMapper.toDto(actorFromDb);
    }

    @Override
    public ActorResponseDto saveActor(final ActorCreateDto createDto) {
        logger.info("[Service]: Saving actor: {}", createDto);
        Actor actor = actorMapper.toModel(createDto);
        actorRepository.save(actor);
        return actorMapper.toDto(actor);
    }

    @Override
    public ActorResponseDto updateById(final Long id, final ActorUpdateDto updateDto) {
        logger.info("[Service]: Updating actor with id {}: {}", id, updateDto);
        Actor actorFromDb = actorRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_ACTOR_BY_ID_MSG + "{}", id);
                    return new EntityNotFoundException(CANNOT_FIND_ACTOR_BY_ID_MSG + id);
                });
        setUpdatedFields(actorFromDb, updateDto);
        actorRepository.save(actorFromDb);
        return actorMapper.toDto(actorFromDb);
    }

    @Override
    public void deleteById(final Long id) {
        logger.info("[Service]: Deleting actor with id: {}", id);
        actorRepository.deleteById(id);
    }

    @Override
    public Page<ActorResponseDto> searchByName(final String name, final Pageable pageable) {
        logger.info("[Service]: Searching for actors by name (pageable): {}", name);
        return actorRepository.findByNameStartingWithIgnoreCase(name, pageable)
                .map(actorMapper::toDto);
    }

    @Override
    public List<ActorResponseDto> searchByName(final String name) {
        logger.info("[Service]: Searching for actors by name (list): {}", name);
        return actorRepository.findByNameStartingWithIgnoreCase(name)
                .stream()
                .map(actorMapper::toDto)
                .toList();
    }

    private void setUpdatedFields(final Actor actorFromDb, final ActorUpdateDto updateDto) {
        Optional.ofNullable(updateDto.getAge()).ifPresent(actorFromDb::setAge);
        Optional.ofNullable(updateDto.getDescription()).ifPresent(actorFromDb::setDescription);
        Optional.ofNullable(updateDto.getName()).ifPresent(actorFromDb::setName);
    }
}
