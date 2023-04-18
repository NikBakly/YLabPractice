package io.ylab.intensive.lesson03.orgStructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OrgStructureParserImpl implements OrgStructureParser {
    private final Map<Long, Employee> employees = new HashMap<>();

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            String[] columnsName = bufferedReader.readLine().split(";");
            while (bufferedReader.ready()) {
                String[] employeeProperties = bufferedReader.readLine().split(";");
                Employee newEmployee = getEmployee(employeeProperties, columnsName);
                employees.put(newEmployee.getId(), newEmployee);
            }
        }
        initializingEmployeeHierarchy(employees.keySet());
        return employees.values().stream()
                .filter(employee -> employee.getBossId() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CEO not found"));
    }

    private Employee getEmployee(String[] employeeProperties, String[] columnsName) {
        Employee newEmployee = new Employee();
        for (int i = 0; i < columnsName.length; i++) {
            switch (columnsName[i]) {
                case "id":
                    if (employeeProperties[i].isEmpty()) {
                        throw new RuntimeException("Employee id can't be null");
                    } else {
                        newEmployee.setId(Long.valueOf(employeeProperties[i]));
                    }
                    break;
                case "boss_id":
                    if (employeeProperties[i].isEmpty()) {
                        newEmployee.setBossId(null);
                    } else {
                        newEmployee.setBossId(Long.valueOf(employeeProperties[i]));
                    }
                    break;
                case "name":
                    newEmployee.setName(employeeProperties[i]);
                    break;
                case "position":
                    newEmployee.setPosition(employeeProperties[i]);
                    break;
            }
        }
        return newEmployee;
    }

    private void initializingEmployeeHierarchy(Set<Long> employeeIds) {
        for (Long employeeId : employeeIds) {
            Employee employee = employees.get(employeeId);
            if (employees.containsKey(employee.getBossId())) {
                Employee employeeBoss = employees.get(employee.getBossId());
                employee.setBoss(employeeBoss);
                employeeBoss.getSubordinate().add(employee);
            }
        }
    }
}
