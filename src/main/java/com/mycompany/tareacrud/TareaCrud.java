/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.tareacrud;

import java.util.Scanner;

/**
 *
 * @author pipe2
 */
public class TareaCrud {

    public static void main(String[] args) {
        
        DAO<User> TDAO = (DAO<User>) DAO.getInstance();
        DAO<Carro> CDAO = (DAO<Carro>) DAO.getInstance();
        
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
        
        // Llamar al método create de DAO para insertar los datos en la base de datos
        CDAO.read(carroId);
        
        // Cerrar el scanner
        scanner.close();
    }
}
