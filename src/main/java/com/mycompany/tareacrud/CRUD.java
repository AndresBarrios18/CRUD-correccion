/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tareacrud;

import java.util.List;

/**
 *
 * @author pipe2
 */
public interface CRUD<T> {
    
    T create (T entity);
    T read(int id);
    T update(T entity);
    void delete(int id);
    List<T> getAll();
    
}
