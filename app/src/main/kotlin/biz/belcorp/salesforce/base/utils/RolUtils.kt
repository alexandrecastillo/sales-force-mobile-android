package biz.belcorp.salesforce.base.utils

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

fun Rol?.isDv() = this == Rol.DIRECTOR_VENTAS

fun Rol?.isGr() = this == Rol.GERENTE_REGION

fun Rol?.isGz() = this == Rol.GERENTE_ZONA

fun Rol?.isSe() = this == Rol.SOCIA_EMPRESARIA

fun Rol?.isDvOrGrOrGz() = this.isDv() || this.isGr() || this.isGz()

fun Rol?.isDvOrGrOrGzOrSe() = this.isDvOrGrOrGz() || this.isSe()

fun Rol?.isDvOrGr() = this.isDv() || this.isGr()

fun Rol?.isGzOrSe() = this.isSe() || this.isGz()
