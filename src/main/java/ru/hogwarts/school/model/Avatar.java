package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;

    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;

    @OneToOne
    private Student student;

    public Avatar() {
    }

    public Avatar(Long id, String filepath, long fileSize, String mediaType, Student student) {
        this.id = id;
        this.filePath = filepath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.student = student;
    }
}
