package com.cheroliv.opticfiber.entity.dao


import com.cheroliv.opticfiber.domain.enumerations.TypeInterEnum
import com.cheroliv.opticfiber.entity.InterEntity
import groovy.transform.TypeChecked
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param

import java.time.LocalDateTime

@TypeChecked
interface InterDao extends
        InterDaoGeneric<InterEntity>,
        PagingAndSortingRepository<InterEntity, Long>,
        ExtendedDao<InterEntity, Long> {

    @Query('from InterEntity i where i.nd=:nd and typeInter=:type')
    Optional<InterEntity> find(
            @Param('nd') String nd,
            @Param('type') TypeInterEnum type)

    @Query("""
        select distinct month(i.dateTimeInter),
        year(i.dateTimeInter) from InterEntity i
        order by year(i.dateTimeInter) asc,
        month(i.dateTimeInter) asc""")
    List<List<Integer>> distinctMoisParAnnee()

    @Query("""
        select distinct month(i.dateTimeInter),
        year(i.dateTimeInter) from InterEntity i
        where i.dateTimeInter between :startDate and :endDate
        order by year(i.dateTimeInter) asc,
        month(i.dateTimeInter) asc""")
    List<List<Integer>> distinctMoisParAnnee(
            @Param('startDate') LocalDateTime startDate,
            @Param('endDate') LocalDateTime endDate)


    @Query("""
        select i from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee""")
    List<InterEntity> findAllDeMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and typeInter='PLP'""")
    Integer countPlpParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and 
        (i.typeInter='BAAP' or i.typeInter='BAOC' or 
        i.typeInter='BAFA' or i.typeInter='BAST') and 
        i.contract!='Passage de cable'""")
    Integer countRacParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee""")
    Integer countInterParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and 
        typeInter='BAFA' and contract!='Passage de cable'""")
    Integer countBafaParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and 
        typeInter='BAST' and contract!='Passage de cable'""")
    Integer countBastParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and typeInter='SAV'""")
    Integer countSavParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and 
        contract='Passage de cable'""")
    Integer countPdcParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and 
        contract='Passage de cable' and typeInter='BAAP'""")
    Integer countPdcBaapParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and 
        contract='Passage de cable' and typeInter='BAOC'""")
    Integer countPdcBaocParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and 
        contract='Passage de cable' and typeInter='BAFA'""")
    Integer countPdcBafaParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and 
        contract='Passage de cable' and typeInter='BAST'""")
    Integer countPdcBastParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and typeInter='BAOC' 
        and contract!='Passage de cable'""")
    Integer countBaocParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and typeInter='BAAP' 
        and contract!='Passage de cable'""")
    Integer countBaapParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select count(i) from InterEntity i 
        where month(i.dateTimeInter)=:mois and 
        year(i.dateTimeInter)=:annee and 
        (typeInter='BAAP' or typeInter='BAOC') and 
        contract='Passage de cable'""")
    Integer countPdcBaocBaapParMoisDansAnnee(
            @Param("mois") Integer mois,
            @Param("annee") Integer annee)

    @Query("""
        select i from InterEntity i 
        where i.id=(select min(j.id) from InterEntity j)""")
    Optional<InterEntity> findByIdMin()

    @Query("""
        select i from InterEntity i 
        where i.id=(select max(j.id) from InterEntity j)""")
    Optional<InterEntity> findByIdMax()

    @Query("""
        select i from InterEntity i
        where i.dateTimeInter=
        (select min(j.dateTimeInter) from InterEntity j)""")
    Optional<InterEntity> findOldestInter()

    @Query("""
        select i from InterEntity i
        where i.dateTimeInter=
        (select max(j.dateTimeInter) from InterEntity j)""")
    Optional<InterEntity> findLatestInter()

    @Query("""
        select i.dateTimeInter from InterEntity i""")
    List<LocalDateTime> findAllDates()
}
