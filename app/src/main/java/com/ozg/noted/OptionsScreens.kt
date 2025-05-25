package com.ozg.noted

import android.support.annotation.DrawableRes

sealed class OptionsScreens (
    @DrawableRes val icon : Int,
    val title : String,
    val path : String
){
    object ScreenCrearNota : OptionsScreens(R.drawable.icon_file, "Crear nueva nota","ScreenCrearNota")
    object ScreenCrearCarpeta : OptionsScreens(R.drawable.icon_folder, "Crear nueva carpeta","ScreenCrearCarpeta")
    object ScreenGraphView : OptionsScreens(R.drawable.icon_graph, "Ver grafo de notas","ScreenGraphView")
    object ScreenExploradorArchivos : OptionsScreens(R.drawable.explorador_archivos, "Explorador de archivos","ScreenExploradorArchivos")
}
