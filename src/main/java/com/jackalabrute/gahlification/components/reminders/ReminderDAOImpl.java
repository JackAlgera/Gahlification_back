package com.jackalabrute.gahlification.components.reminders;

import com.jackalabrute.gahlification.database.daos.DefaultDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class ReminderDAOImpl implements DefaultDAO<Reminder> {

    @Autowired
    private RemindersRepository remindersRepository;

    public Reminder create(Reminder item) {
        return remindersRepository.save(item);
    }

    public void delete(UUID id) {
        remindersRepository.deleteById(id);
    }

    public Optional<Reminder> findById(UUID id) {
        return remindersRepository.findById(id);
    }

    @Override
    public List<Reminder> findAll() {
        return remindersRepository.findAll();
    }

    @Override
    public Reminder update(Reminder item) {
        return remindersRepository.save(item);
    }
}
