package com.example.cinema.service.impl;

import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.DirectorMapper;
import com.example.cinema.model.Director;
import com.example.cinema.repository.DirectorRepository;
import com.example.cinema.service.DirectorService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private static final String CANNOT_FIND_DIRECTOR_BY_ID_MSG = "Cannot find director by Id: ";
    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    @Override
    @Transactional // will replace with join once i implement the movie logic
    public Page<DirectorResponseDto> searchByName(String name, Pageable pageable) {
        Page<Director> directorsPage = directorRepository
                .findByNameStartingWithIgnoreCase(name, pageable);
        return directorsPage.map(directorMapper::toDto);
    }

    @Override
    @Transactional // will replace with join once i implement the movie logic
    public Page<DirectorResponseDto> findAll(Pageable pageable) {
        Page<Director> directorsPage = directorRepository.findAll(pageable);
        return directorsPage.map(directorMapper::toDto);
    }

    @Override
    public DirectorResponseDto findById(Long id) {
        Director directorFromDb = directorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CANNOT_FIND_DIRECTOR_BY_ID_MSG + id)
        );
        return directorMapper.toDto(directorFromDb);
    }

    @Override
    public DirectorResponseDto saveDirector(DirectorCreateDto createDto) {
        Director director = directorMapper.toModel(createDto);
        directorRepository.save(director);
        return directorMapper.toDto(director);
    }

    @Override
    @Transactional // will replace with join once i implement the movie logic
    public DirectorResponseDto updateDirector(Long id, DirectorUpdateDto updateDto) {
        Director directorFromDb = directorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CANNOT_FIND_DIRECTOR_BY_ID_MSG + id)
        );
        setUpdatedFields(directorFromDb, updateDto);
        directorRepository.save(directorFromDb);
        return directorMapper.toDto(directorFromDb);
    }

    @Override
    public void deleteById(Long id) {
        directorRepository.deleteById(id);
    }

    private void setUpdatedFields(Director directorFromDb, DirectorUpdateDto updateDto) {
        Optional.ofNullable(updateDto.getName()).ifPresent(directorFromDb::setName);
        Optional.ofNullable(updateDto.getDescription()).ifPresent(directorFromDb::setDescription);
        // setting movies if the list is present (will add once I implement the movie logic)
    }
}
