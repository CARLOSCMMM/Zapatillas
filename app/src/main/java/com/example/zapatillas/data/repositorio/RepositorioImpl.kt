package com.example.zapatillas.data.repositorio

import com.example.zapatillas.domain.model.Zapatilla
import com.example.zapatillas.domain.repositorio.Repositorio

class RepositorioImpl : Repositorio {

    private val listaZapatillas: MutableList<Zapatilla> = mutableListOf(
        Zapatilla(
            1,
            "Air Jordan 1 Chicago",
            "Nike",
            180.0,
            "https://newcop.com/cdn/shop/files/jordan-1-union_01.png?v=1741346143"
        ),
        Zapatilla(
            2,
            "Yeezy Boost 350 V2",
            "Adidas",
            230.0,
            "https://cdn-images.farfetch-contents.com/12/96/03/31/12960331_23445016_600.jpg"
        ),
        Zapatilla(
            3,
            "Old Skool Classic",
            "Vans",
            75.0,
            "https://novalbos.com/wp-content/uploads/2023/11/CLASSSIC.jpg"
        ),
        Zapatilla(
            4,
            "Suede Classic XXI",
            "Puma",
            80.0,
            "https://stayhard.com/cdn/shop/products/60527-68_001.png?v=1680159533"
        ),
        Zapatilla(
            5,
            "New Balance 550",
            "New Balance",
            120.0,
            "https://images.stockx.com/images/New-Balance-550-White-Grey-Product.jpg?fit=fill&bg=FFFFFF&w=700&h=500&fm=webp&auto=compress&q=90&dpr=2&trim=color"
        ),
        Zapatilla(
            6,
            "Chuck Taylor 70 Hi",
            "Converse",
            95.0,
            "https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/202406/10/00182369601917____13__1200x1200.jpg"
        ),
        Zapatilla(
            7,
            "Dunk Low Panda",
            "Nike",
            110.0,
            "https://images.stockx.com/images/Nike-Dunk-Low-Retro-White-Black-2021-Product.jpg?fit=fill&bg=FFFFFF&w=700&h=500&fm=webp&auto=compress&q=90&dpr=2&trim=color"
        ),
        Zapatilla(
            8,
            "Samba OG",
            "Adidas",
            100.0,
            "https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/3bbecbdf584e40398446a8bf0117cf62_9366/Zapatillas_Samba_OG_Blanco_B75806_01_standard.jpg"
        )
    )

    override fun getZapatillas(): List<Zapatilla> = listaZapatillas.toList()

    override fun addZapatilla(zapatilla: Zapatilla) {
        val newId = (listaZapatillas.maxOfOrNull { it.id } ?: 0) + 1
        listaZapatillas.add(zapatilla.copy(id = newId))
    }

    override fun updateZapatilla(old: Zapatilla, new: Zapatilla) {
        val index = listaZapatillas.indexOf(old)
        if (index != -1) {
            listaZapatillas[index] = new
        }
    }

    override fun deleteZapatilla(zapatilla: Zapatilla) {
        listaZapatillas.remove(zapatilla)
    }
}