package org.nicolaswasaff.pokedex.model;

// Contrato que cumplen las cartas que participan del sistema de evolución
// dentro de la colección.
public interface Evolucionable {

    // Indica si la carta tiene su evolución activa en la colección.
    // @return true si la evolución está activa
    boolean tieneEvolucionActiva();

    // Registra que la carta activa su evolución.
    void activarEvolucion();
}