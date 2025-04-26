package main.gestion_des_taches.service;

import main.gestion_des_taches.dao.LabelDAO;
import main.gestion_des_taches.model.Label;

import java.util.List;
import java.util.Optional;

public class LabelService {
    private final LabelDAO labelDAO;

    public LabelService() {
        this.labelDAO = new LabelDAO();
    }

    public Label ajouterLabel(String nom) {
        Label label = new Label(nom);
        return labelDAO.save(label);
    }

    public Optional<Label> getById(int id) {
        return labelDAO.findById(id);
    }

    public List<Label> getAll() {
        return labelDAO.findAll();
    }

    public List<Label> getByTacheId(int tacheId) {
        return labelDAO.findByTacheId(tacheId);
    }

    public boolean update(Label label) {
        return labelDAO.update(label);
    }

    public boolean delete(int id) {
        return labelDAO.delete(id);
    }
}