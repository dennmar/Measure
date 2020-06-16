package com.example.measure;

public class MockLoginRepository implements LoginRepository {
    @Override
    public User getCurrentUser() {
        return null;
    }
}
