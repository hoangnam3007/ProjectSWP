package Utils;

import model.Question;
import model.Answer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    public static List<Question> readQuestionsFromExcel(InputStream inputStream) {
        List<Question> questions = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            Question currentQuestion = null;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }

                Cell questionCell = row.getCell(0);
                Cell answerCell = row.getCell(1);
                Cell isCorrectCell = row.getCell(2);

                if (questionCell != null && !questionCell.toString().isEmpty()) {
                    // New question
                    currentQuestion = new Question();
                    currentQuestion.setQuestion_name(getCellValueAsString(questionCell));
                    currentQuestion.setAnswers(new ArrayList<>());
                    questions.add(currentQuestion);
                }

                if (currentQuestion != null) {
                    // Add answer to the current question
                    Answer answer = new Answer();
                    answer.setAnswer_content(getCellValueAsString(answerCell));
                    answer.setIsCorrect(isCorrectCell != null && "yes".equalsIgnoreCase(getCellValueAsString(isCorrectCell)));
                    currentQuestion.getAnswers().add(answer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return cell.toString();
        }
    }
}
