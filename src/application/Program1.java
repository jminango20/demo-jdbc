package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== TESTE 1: Department findById ===");		
		Department department = departmentDao.findById(3);
		System.out.println(department);

		System.out.println("=== TESTE 2: Department findAll ===");		
		List<Department> list = new ArrayList<Department>();
		list = departmentDao.findAll();
		for(Department depar : list) {
			System.out.println(depar);
		}
		
		System.out.println("\n=== TESTE 3: seller insert ===");
		Department newDepartment = new Department(5, "Pets");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New id = " + newDepartment.getId());
		
		System.out.println("\n=== TEST 4: update =======");
		Department dep2 = departmentDao.findById(1);
		dep2.setName("Food");
		departmentDao.update(dep2);
		System.out.println("Update completed");
		
		System.out.println("\n=== TEST 5: delete =======");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed");
		
		
		
		sc.close();

	}

}
