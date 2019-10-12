package com.cheroliv.opticfiber.inter.repository

import com.cheroliv.opticfiber.inter.domain.InterDto
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum


class InterRepositoryStub implements InterRepository{
    @Override
    Optional<InterDto> find(String nd, TypeInterEnum type) {
        return null
    }

    @Override
    List<InterDto> findAll() {
        return null
    }

    @Override
    List<List<Integer>> distinctMoisParAnnee() {
        return null
    }

    @Override
    List<InterDto> findAllDeMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countPlpParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countRacParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countInterParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countBafaParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countBastParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countSavParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countPdcParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countPdcBaapParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countPdcBaocParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countPdcBafaParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countPdcBastParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countBaocParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countBaapParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Integer countPdcBaocBaapParMoisDansAnnee(Integer mois, Integer annee) {
        return null
    }

    @Override
    Optional<InterDto> findByIdMin() {
        return null
    }

    @Override
    Optional<InterDto> findByIdMax() {
        return null
    }

    @Override
    Optional<InterDto> findById(Long id) {
        return null
    }

    @Override
    InterDto save(InterDto interDto) {
        return null
    }

    @Override
    void deleteById(Long id) {

    }
}
