package com.cheroliv.opticfiber.inter.repository

import com.cheroliv.opticfiber.inter.domain.InterDto
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.inter.entity.InterEntity
import com.cheroliv.opticfiber.inter.entity.dao.InterDao
import groovy.transform.TypeChecked
import org.springframework.stereotype.Repository

import java.time.LocalDateTime

@Repository
@TypeChecked
class InterRepositoryImp implements InterRepository {
    final InterDao interDao

    InterRepositoryImp(InterDao interDao) {
        this.interDao = interDao
    }

    InterDto save(InterDto dto) {
        interDao.save(InterEntity.fromDto(dto)).toDto()
    }

    @Override
    void deleteById(Long id) {
        interDao.deleteById(id)
    }

    @Override
    Optional<InterDto> find(String nd, TypeInterEnum type) {
        Optional<InterEntity> result = interDao.find(nd, type)
        if (result.empty) Optional.empty()
        else Optional.of(InterEntity.fromEntity(result.get()))
    }

    @Override
    List<InterDto> findAll() {
        interDao.findAll().collect { InterEntity it ->
            it.toDto()
        }
    }

    @Override
    List<List<Integer>> distinctMoisParAnnee() {
        interDao.distinctMoisParAnnee()
    }

    @Override
    List<List<Integer>> distinctMoisParAnnee(
            LocalDateTime startDate,
            LocalDateTime endDate) {
        interDao.distinctMoisParAnnee(
                startDate, endDate)
    }

    @Override
    List<InterDto> findAllDeMoisDansAnnee(Integer mois, Integer annee) {
        interDao.findAllDeMoisDansAnnee(mois, annee).collect {
            InterEntity it ->
                it.toDto()
        }
    }

    @Override
    Integer countPlpParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countPlpParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countRacParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countRacParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countInterParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countInterParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countBafaParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countBafaParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countBastParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countBastParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countSavParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countSavParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countPdcParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countPdcParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countPdcBaapParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countPdcBaapParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countPdcBaocParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countPdcBaocParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countPdcBafaParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countPdcBafaParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countPdcBastParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countPdcBastParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countBaocParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countBaocParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countBaapParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countBaapParMoisDansAnnee(mois, annee)
    }

    @Override
    Integer countPdcBaocBaapParMoisDansAnnee(Integer mois, Integer annee) {
        interDao.countPdcBaocBaapParMoisDansAnnee(mois, annee)
    }

    @Override
    Optional<InterDto> findByIdMin() {
        Optional<InterEntity> result = interDao.findByIdMin()
        if (result.empty) Optional.empty()
        else Optional.of(result.get().toDto())
    }

    @Override
    Optional<InterDto> findByIdMax() {
        Optional<InterEntity> result = interDao.findByIdMax()
        if (result.empty) Optional.empty()
        else Optional.of(result.get().toDto())
    }

    @Override
    Optional<InterDto> findById(Long id) {
        Optional<InterEntity> result = interDao.findById(id)
        if (result.empty) Optional.empty()
        else Optional.of(result.get().toDto())
    }

    @Override
    Optional<InterDto> findOldestInter() {
        Optional<InterEntity> result =
                interDao.findOldestInter()
        if (result.empty) Optional.empty()
        else Optional.of(result.get().toDto())
    }

    @Override
    Optional<InterDto> findLatestInter() {
        Optional<InterEntity> result =
                interDao.findLatestInter()
        if (result.empty) Optional.empty()
        else Optional.of(result.get().toDto())
    }

    List<LocalDateTime> findAllDates() {
        interDao.findAllDates()
    }
}
