package br.com.fiap.mechai.service;

import br.com.fiap.mechai.dao.MechanicDAO;
import br.com.fiap.mechai.model.Mechanic;


import java.util.List;

public class MechanicService {
    private MechanicDAO mechanicDAO = new MechanicDAO();

    public void registerMechanic(Mechanic mechanic) {
        mechanicDAO.createMechanic(mechanic);
    }

    public Mechanic getMechanicById(int id) {
        return mechanicDAO.readMechanic(id);
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicDAO.readAllMechanics();
    }

    public void updateMechanic(Mechanic mechanic) {
        mechanicDAO.updateMechanic(mechanic);
    }

    public void deleteMechanic(int id) {
        mechanicDAO.deleteMechanic(id);
    }
}
