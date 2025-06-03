package com.rodriguez.smartfitv2.data.repository

import com.rodriguez.smartfitv2.data.model.ClothingItem

//BASE DE DATOS FICTICIA PARA PROBAR BOTON BUSQUEDA
class ClothingRepository {
    private val clothes = listOf(

        ClothingItem(
            id = 1,
            name = "Pantalón vaquero azul",
            type = "pantalón",
            waist = 80,
            chest = 95,
            hip = 100,
            leg = 110,
            stock = 5
        ),
        ClothingItem(
            id = 2,
            name = "Pantalón vaquero negro",
            type = "pantalón",
            waist = 85,
            chest = 98,
            hip = 102,
            leg = 112,
            stock = 2
        ),
        ClothingItem(
            id = 3,
            name = "Falda plisada",
            type = "falda",
            waist = 70,
            chest = 90,
            hip = 95,
            leg = 100,
            stock = 3
        ),
        ClothingItem(
            id = 4,
            name = "Shorts deportivos",
            type = "pantalón",
            waist = 80,
            chest = 92,
            hip = 98,
            leg = 105,
            stock = 4
        ),
        ClothingItem(
            id = 5,
            name = "Pantalón chino beige",
            type = "pantalón",
            waist = 90,
            chest = 100,
            hip = 105,
            leg = 115,
            stock = 1
        )
    )

    fun getAllClothes(): List<ClothingItem> = clothes
}
