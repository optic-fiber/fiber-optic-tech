package com.cheroliv.opticfiber.inter.repository

import com.cheroliv.opticfiber.inter.domain.InterDto
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum

import java.time.LocalDateTime

interface InterRepository {
    Optional<InterDto> find(String nd,
                            TypeInterEnum type)

    List<InterDto> findAll()

    List<List<Integer>> distinctMoisParAnnee()

    List<List<Integer>> distinctMoisParAnnee(
            LocalDateTime startDate,
            LocalDateTime endDate)

    List<InterDto> findAllDeMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countPlpParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countRacParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countInterParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countBafaParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countBastParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countSavParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countPdcParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countPdcBaapParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countPdcBaocParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countPdcBafaParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countPdcBastParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countBaocParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countBaapParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Integer countPdcBaocBaapParMoisDansAnnee(
            Integer mois,
            Integer annee)

    Optional<InterDto> findByIdMin()


    Optional<InterDto> findByIdMax()

    Optional<InterDto> findById(Long id)

    InterDto save(InterDto interDto)

    void deleteById(Long id)

    Optional<InterDto> findOldestInter()

    Optional<InterDto> findLatestInter()

    List<LocalDateTime> findAllDates()
}
