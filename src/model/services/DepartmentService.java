package model.services;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    private DepartmentDAO dao = DAOFactory.createDepartmentDAO();

    public List<Department> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Department dp) {
        if (dp.getId() == null) {
            dao.insert(dp);
        } else {
            dao.update(dp);
        }
    }

    public void remove(Department obj) {
        dao.deleteById(obj.getId());
    }
}
