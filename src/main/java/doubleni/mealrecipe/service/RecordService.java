package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.Record;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.repository.RecordRepository;
import doubleni.mealrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.RECORD_SAVE_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    public void saveRecord (List<String> recommendedRecipe, Long userId) throws BaseException {
        try {
            User user = userRepository.findById(userId).orElse(null);

            Record record = recordRepository.findByUser(user).orElse(new Record());
            record.setRecordNum(recommendedRecipe);
            record.setUser(user);

            recordRepository.save(record);
        } catch (Exception exception){
            throw new BaseException(RECORD_SAVE_ERROR);
        }
    }

}
