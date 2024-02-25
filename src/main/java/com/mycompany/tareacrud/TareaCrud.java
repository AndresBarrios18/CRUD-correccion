package com.mycompany.tareacrud;

import dao.DAO;
import java.util.Scanner;

public class TareaCrud {

    public static void main(String[] args) {
        
        // Crear instancias de DAO para User y Carro
        DAO<User> userDAO = (DAO<User>) DAO.getInstance();
        DAO<Carro> carroDAO = (DAO<Carro>) DAO.getInstance();
        
        // Solicitar datos del usuario por consola
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Ingrese los datos del usuario:");
        System.out.print("ID: ");
        int userId = scanner.nextInt();
        System.out.print("Nombre: ");
        String userName = scanner.next();
        System.out.print("Email: ");
        String userEmail = scanner.next();
        
        // Crear objeto User con los datos ingresados
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setEmail(userEmail);
        
        // Llamar al método create de UserDAO para insertar los datos en la base de datos
        userDAO.create(user);
        
        // Solicitar datos del carro por consola
        System.out.println("Ingrese los datos del carro:");
        System.out.print("ID: ");
        int carroId = scanner.nextInt();
        System.out.print("Color: ");
        String carroColor = scanner.next();
        System.out.print("Marca: ");
        String carroMarca = scanner.next();
        
        // Crear objeto Carro con los datos ingresados
        Carro carro = new Carro();
        carro.setId(carroId);
        carro.setColor(carroColor);
        carro.setMarca(carroMarca);
        
        // Llamar al método create de CarroDAO para insertar los datos en la base de datos
        userDAO.create(user);
        carroDAO.create(carro);
        
        // Cerrar el scanner
        scanner.close();
    }
}
