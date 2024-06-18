package com.example.cinema.service.impl;

import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.DirectorMapper;
import com.example.cinema.model.Director;
import com.example.cinema.repository.DirectorRepository;
import com.example.cinema.service.DirectorService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private static final String CANNOT_FIND_DIRECTOR_BY_ID_MSG = "Cannot find director by Id: ";
    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;
    private final Logger logger;

    @Override
    public Page<DirectorResponseDto> searchByName(final String name, final Pageable pageable) {
        logger.info("[Service]: Searching directors (pageable) by name: {}", name);
        Page<Director> directorsPage = directorRepository
                .findByNameStartingWithIgnoreCase(name, pageable);
        return directorsPage.map(directorMapper::toDto);
    }

    @Override
    public List<DirectorResponseDto> searchByName(final String name) {
        logger.info("[Service]: Searching directors (list) by name: {}", name);
        return directorRepository.findByNameStartingWithIgnoreCase(name)
                .stream()
                .map(directorMapper::toDto)
                .toList();
    }

    @Override
    public Page<DirectorResponseDto> findAll(final Pageable pageable) {
        logger.info("[Service]: Finding all directors");
        Page<Director> directorsPage = directorRepository.findAll(pageable);
        return directorsPage.map(directorMapper::toDto);
    }

    @Override
    public DirectorResponseDto findById(final Long id) {
        logger.info("[Service]: Finding director by id: {}", id);
        Director directorFromDb = directorRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_DIRECTOR_BY_ID_MSG + "{}", id);
                    return new EntityNotFoundException(CANNOT_FIND_DIRECTOR_BY_ID_MSG + id);
                }
        );
        return directorMapper.toDto(directorFromDb);
    }

    @Override
    public DirectorResponseDto saveDirector(final DirectorCreateDto createDto) {
        logger.info("[Service]: Saving director: {}", createDto);
        Director director = directorMapper.toModel(createDto);
        directorRepository.save(director);
        return directorMapper.toDto(director);
    }

    @Override
    public DirectorResponseDto updateDirector(final Long id, final DirectorUpdateDto updateDto) {
        logger.info("[Service]: Updating director with id: {}, updateDto: {}", id, updateDto);
        Director directorFromDb = directorRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_DIRECTOR_BY_ID_MSG + "{}", id);
                    return new EntityNotFoundException(CANNOT_FIND_DIRECTOR_BY_ID_MSG + id);
                }
        );
        setUpdatedFields(directorFromDb, updateDto);
        directorRepository.save(directorFromDb);
        return directorMapper.toDto(directorFromDb);
    }

    @Override
    public void deleteById(final Long id) {
        logger.info("[Service]: Deleting director by id: {}", id);
        directorRepository.deleteById(id);
    }

    private void setUpdatedFields(final Director directorFromDb,
                                  final DirectorUpdateDto updateDto) {
        Optional.ofNullable(updateDto.getName()).ifPresent(directorFromDb::setName);
        Optional.ofNullable(updateDto.getDescription()).ifPresent(directorFromDb::setDescription);
    }
}
