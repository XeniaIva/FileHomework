import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipReaderTests {

    ClassLoader classLoader = ZipReaderTests.class.getClassLoader();

    @Test
    void readCsvFromZip() throws Exception {
        InputStream is = classLoader.getResourceAsStream("archive.zip");
        ZipInputStream zip = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().contains("CSV.csv")) {
                CSVReader csvReader = new CSVReader(new InputStreamReader(zip, UTF_8));
                List<String[]> csv = csvReader.readAll();
                assertThat(csv).contains(
                        new String[]   {"предмет", "фамилия", "оценка", "класс"},
                        new String[]   {"математика", "Иванов", "4", "7"},
                        new String[]   {"физика", "Петров", "5", "10"},
                        new String[]   {"информатика", "Степанов", "3", "9"},
                        new String[]   {"литература", "Иванова", "5", "5"}
                );
            }
        }
    }

    @Test
    void readPdfFromZip() throws Exception {
        InputStream is = classLoader.getResourceAsStream("archive.zip");
        ZipInputStream zip = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zipFile = new ZipFile(new File("src/test/resources/" + "archive.zip"));
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().contains("PDF.pdf")) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    PDF pdf = new PDF(stream);
                    assertThat(pdf.text).contains("Всем привет! Это мое домашнее задание.");
                }
            }
        }
    }

    @Test
    void readXlsFromZip() throws Exception {
        InputStream is = classLoader.getResourceAsStream("archive.zip");
        ZipInputStream zip = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zipFile = new ZipFile(new File("src/test/resources/" + "archive.zip"));
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().contains("XLS.xlsx")) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    XLS xls = new XLS(stream);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(1)
                            .getCell(3)
                            .getStringCellValue()).contains("сметана");
                }
            }
        }
    }
}
