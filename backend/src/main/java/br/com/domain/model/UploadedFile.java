package br.com.domain.model;


public class UploadedFile {
    private String fileName;
    private byte[] content;

    // Construtor
    public UploadedFile(String fileName, byte[] content) {
        this.fileName = fileName;
        this.content = content;
    }

    // Getters e setters
    public String getFileName() {
        return fileName;
    }

    public byte[] getContent() {
        return content;
    }
}
