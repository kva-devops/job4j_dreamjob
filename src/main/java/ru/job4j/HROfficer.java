package ru.job4j;

import java.util.ArrayList;
import java.util.List;

public class HROfficer {
    int id;
    String name;
    String companyName;
    String phoneNumber;
    List<Vacancy> vacancyList = new ArrayList<>();

    public HROfficer(int id, String name, String companyName, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    public void publishVacancy(Vacancy vacancy) {
        this.vacancyList.add(vacancy);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Vacancy> getVacancyList() {
        return vacancyList;
    }
}
