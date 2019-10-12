package com.cheroliv.opticfiber.inter.entity.dao

import com.cheroliv.opticfiber.inter.entity.InterEntity
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.repository.ExtendedRepository
import groovy.transform.TypeChecked
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@TypeChecked
interface InterDao extends JpaRepository<InterEntity, Long>, ExtendedRepository<InterEntity, Long> {

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
        where i=(select max(j.id) from InterEntity j)""")
    Optional<InterEntity> findByIdMax()
}
