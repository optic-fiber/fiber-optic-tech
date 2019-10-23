package com.cheroliv.opticfiber.inter.service


import com.cheroliv.opticfiber.inter.domain.InterDto

interface InterService {
    InterDto find(String nd, String type)

    InterDto getFirst()

    InterDto getPrevious(Long id)

    InterDto save(InterDto interDto)

    InterDto getNext(Long id)

    InterDto getLast()

    InterDto get(Long id)

    Boolean isUniqueIndexAvailable(String nd, String type)

    void delete(Long id)

    InterDto findById(Long id)

    Boolean isUniqueIndexConsistent(Long id, String nd, String type)

    InterDto saveWithPatch(InterDto interDto)

    List<InterDto> getAll()
}
