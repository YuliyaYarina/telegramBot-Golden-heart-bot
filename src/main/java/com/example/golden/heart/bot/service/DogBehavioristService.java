package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.DogBehaviorist;
import com.example.golden.heart.bot.repository.DogBehavioristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogBehavioristService {

    @Autowired
    private DogBehavioristRepository dogBehavioristRepo;

    /**
     * Сохраняет в БД киногола
     * @param dogBehaviorist
     * @return возвращает сохранненого кинолога
     */
    public DogBehaviorist save(DogBehaviorist dogBehaviorist) {
        return dogBehavioristRepo.save(dogBehaviorist);
    }

    /**
     * Редактирует информацию о кинологе
     * @param id
     * @param dogBehaviorist
     * @return возвращает изменненую информацию кинолога
     */
    public DogBehaviorist edite(Long id, DogBehaviorist dogBehaviorist) {
        return dogBehavioristRepo.findById(id)
                .map(fundDogBehaviorist -> {
                    fundDogBehaviorist.setName(dogBehaviorist.getName());
                    fundDogBehaviorist.setPhone(dogBehaviorist.getPhone());
                    return dogBehavioristRepo.save(fundDogBehaviorist);
                }).orElse(null);
    }

    /**
     * Находит в БД кинолога
     * @param id
     * @return возвращает найденного кинолога, или NULL
     */
    public DogBehaviorist getById(Long id) {
        return dogBehavioristRepo.findById(id).orElse(null);
    }


    public boolean remove(Long id) {
        dogBehavioristRepo.deleteById(id);
        return true;
    }

    public List<DogBehaviorist> findAll() {
        return dogBehavioristRepo.findAll();
    }
}
