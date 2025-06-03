package com.rodriguez.smartfitv2.data.model

// DONDE RECOGEMOS LOS DATOS DE LAS PRENDAS PARA PROBAR LA BUSQUEDA DE LAS MISMAS
data class ClothingItem(
    val id: Int,
    val name: String,
    val type: String,
    val waist: Int, // Medida de cintura en cm
    val chest: Int, // Medida de pecho en cm
    val hip: Int, // Medida de cadera en cm
    val leg: Int, // Medida de pierna en cm
    val stock: Int)
