package com.example.springtest;

import com.example.springtest.exception.DecryptionException;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestDao testDao;
    private final StandardPBEStringEncryptor encryptor;

    public String addMessage(TestModel testModel) {
        testModel.setMessage(encryptor.encrypt(testModel.getMessage()));
        testDao.save(testModel);
        return testModel.getMessage();
    }

    public List<TestModel> getMessages() {
        return testDao.findAll().stream()
                .peek(tm -> {
                    try {
                        tm.setMessage(encryptor.decrypt(tm.getMessage()));
                    } catch (EncryptionOperationNotPossibleException e) {
                        throw new DecryptionException("Failed to decrypt credit cards' numbers");
                    }
                })
                .collect(Collectors.toList());
    }

    @Transactional // allows us not to call .save() to update testModelFromDb, because it is on  the persistent state
    public String updateMessage(Long id, TestModel testModel) {
        TestModel testModelFromDb = getTestModelById(id);
        testModelFromDb.setMessage(encryptor.encrypt(testModel.getMessage()));
        return testModelFromDb.getMessage();
    }

    public TestModel getTestModelById(Long id) {
        return testDao.findById(id).orElseThrow(() ->
                new RuntimeException(String.format("Entity with id = %s not found", id)));
    }

    public TestModel getTestModelByIdDecrypted(Long id) {
        TestModel testModel = getTestModelById(id);
        testModel.setMessage(encryptor.decrypt(testModel.getMessage()));
        return testModel;
    }
}
