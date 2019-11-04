package com.cheroliv.opticfiber.inter.service

import com.cheroliv.opticfiber.domain.InterDto
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.inter.repository.InterRepository
import com.cheroliv.opticfiber.inter.service.exception.InterNotFoundException
import com.cheroliv.opticfiber.inter.service.exception.InterTypeEnumException
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@TypeChecked
@Transactional(readOnly = true)
class InterServiceImp implements InterService {


    final InterRepository interRepository

    InterServiceImp(InterRepository interRepository) {
        this.interRepository = interRepository
    }


    static final def FIRST_ITERATION_OF_LIST = 0

    @Override
    InterDto get(Long id) {
        if (!id) return null
        Optional<InterDto> optional = interRepository.findById(id)
        if (optional.empty) return null
        else optional.get()
    }

    @Override
    InterDto getFirst() {
        InterDto result = new InterDto()
        Optional<InterDto> optionalInter = interRepository.findByIdMin()
        if (optionalInter.present)
            result = optionalInter.get()
        result
    }

    @Override
    InterDto getLast() {
        InterDto result = new InterDto()
        Optional<InterDto> optionalInter = interRepository.findByIdMax()
        if (optionalInter.present)
            result = optionalInter.get()
        result
    }

    @Override
    InterDto getPrevious(Long id) {
        Optional<InterDto> optional = interRepository.findById(id)
        if (optional.isEmpty()) return null
        InterDto firstDto = getFirst()
        if (id == firstDto.id) return firstDto
        Optional<InterDto> optionalResult
        while ((optionalResult = interRepository.findById(--id)).empty) {
            if (firstDto.id == optionalResult.get().id ||
                    id < firstDto.id)
                return firstDto
        }
        optionalResult.present ?
                optionalResult.get() :
                optional.get()
    }

    @Override
    InterDto getNext(Long id) {
        Optional<InterDto> optional = interRepository.findById(id)
        if (optional.isEmpty()) return null
        InterDto lastDto = getLast()
        log.info("id : $id")
        if (id == lastDto.id) return lastDto
        Optional<InterDto> optionalResult
        InterDto interMaxId = getLast()
        while ((optionalResult = interRepository.findById(++id)).empty) {
            if (interMaxId.id == optionalResult.get().id ||
                    id > interMaxId.id)
                return interMaxId
        }
        optionalResult.present ?
                optionalResult.get() :
                optional.get()
    }


    @Override
    InterDto findById(Long id) {
        if (!id) return null
        Optional<InterDto> optional = interRepository.findById(id)
        if (optional.isEmpty()) return null
        else optional.get()
    }


    @Override
    InterDto find(String nd, String type) {
        if (!TypeInterEnum.values().collect {
            it.name()
        }.contains(type)) throw new InterTypeEnumException(type)
        Optional<InterDto> result = interRepository.find(
                nd, TypeInterEnum.valueOfName(type))
        if (result.present)
            result.get()
        else throw new InterNotFoundException(nd, type)
    }


    @Override
    Boolean isUniqueIndexConsistent(Long id, String nd, String type) {
        if (!id || !nd || !type) return false
        if (interRepository.findById(id).empty) return false
        Optional<InterDto> optional = interRepository.find(
                nd, TypeInterEnum.valueOfName(type))
        if (optional.empty) return true
        InterDto result = optional.get()
        if (result.id == id) true
        else false
    }


    @Override
    Boolean isUniqueIndexAvailable(String nd, String type) {
        if (!nd || !type) return false
        Optional optional = interRepository.find(
                nd, TypeInterEnum.valueOfName(type))
        if (optional.empty) true
        else false
    }


    @Override
    @Transactional
    InterDto save(InterDto interDto) {
        interRepository.save(interDto)
    }

    @Override
    @Transactional
    void delete(Long id) {
        interRepository.deleteById(id)
    }

    @Override
    @Transactional
    InterDto saveWithPatch(InterDto interDto) {
        interRepository.save(interDto)
    }

    @Override
    List<InterDto> getAll() {
        interRepository.findAll()
    }
}
