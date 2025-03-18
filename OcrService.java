package com.example.ocr.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class OcrService {

    private final ITesseract tesseract;

    public OcrService() {
        this.tesseract = new Tesseract();
        this.tesseract.setDatapath("C:/Tesseract-OCR/tessdata-main/tessdata-main"); // Tesseract 데이터 폴더 경로 설정
        this.tesseract.setLanguage("kor"); // 한국어 OCR 설정
    }

    public String extractText(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("ocr_", ".png");
        file.transferTo(tempFile);

        try {
            return tesseract.doOCR(tempFile);
        } catch (TesseractException e) {
            e.printStackTrace();
            return "OCR 처리 실패";
        } finally {
            tempFile.deleteOnExit(); // 임시 파일 삭제
        }
    }
}
