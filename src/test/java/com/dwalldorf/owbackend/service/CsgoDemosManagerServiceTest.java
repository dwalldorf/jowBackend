package com.dwalldorf.owbackend.service;

import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class CsgoDemosManagerServiceTest extends BaseTest {

    private final static String FOLDER = "csgomanager_xlsx/";
    private final static String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Mock
    private DemoService demoService;

    @Mock
    private UserService userService;

    private CsgoDemosManagerExcelService demosManagerExcelService;

    private CsgoDemosManagerService demosManagerService;

    @Override
    protected void afterSetup() {
        this.demosManagerExcelService = new CsgoDemosManagerExcelService();
        mockLogger(this.demosManagerExcelService);

        this.demosManagerService = new CsgoDemosManagerService(demoService, demosManagerExcelService, userService);
        mockLogger(this.demosManagerService);


    }

    @Test(expected = InvalidInputException.class)
    public void testProcessFile_WrongFileExtension() throws Exception {
        MultipartFile fileMock = Mockito.mock(MultipartFile.class);
        when(fileMock.getOriginalFilename()).thenReturn("someFilename.txt");

        demosManagerService.processFile(fileMock);
    }

    private MultipartFile getMultipartFile(String fileName) throws IOException {
        fileName = this.getClass().getClassLoader().getResource(FOLDER + fileName).getFile();

        File file = new File(fileName);
        FileInputStream inputStream = new FileInputStream(file);
        return new MockMultipartFile("files", file.getName(), CONTENT_TYPE, IOUtils.toByteArray(inputStream));
    }
}