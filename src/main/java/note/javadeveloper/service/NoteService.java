package note.javadeveloper.service;

import note.javadeveloper.dto.NoteDto;
import note.javadeveloper.entity.NoteEntity;
import note.javadeveloper.entity.UserEntity;
import note.javadeveloper.errors.NoteNotFoundError;
import note.javadeveloper.repository.NoteRepository;
import note.javadeveloper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {
    private UserRepository userRepository;
    private NoteRepository noteRepository;

    @Autowired
    private void set(UserRepository userRepository, NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
    }

    public void saveNote(NoteDto noteDto, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        noteRepository.save(NoteEntity
                .builder()
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .userEntity(userEntity)
                .build());
    }

    public void editNote(NoteDto noteDto, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        noteRepository.save(NoteEntity
                .builder()
                .id(noteDto.getId())
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .userEntity(userEntity)
                .build());
    }

    public List<NoteDto> getAllNotes(Long userId) {
        List<NoteEntity> allByUserId = noteRepository.findAllByUserEntityId(userId);
        return allByUserId.stream()
                .map(e -> NoteDto.builder()
                        .id(e.getId())
                        .content(e.getContent())
                        .title(e.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    public NoteDto getNoteById(Long id) {
        NoteEntity noteEntity = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundError("Note with id " + id + " not found"));
        return NoteDto.builder()
                .title(noteEntity.getTitle())
                .content(noteEntity.getContent())
                .id(noteEntity.getId())
                .build();
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}
