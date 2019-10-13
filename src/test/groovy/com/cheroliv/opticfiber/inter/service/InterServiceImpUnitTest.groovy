package com.cheroliv.opticfiber.inter.service

import com.cheroliv.opticfiber.TestData
import com.cheroliv.opticfiber.inter.domain.InterDto
import com.cheroliv.opticfiber.inter.repository.InterRepository
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals
import static org.mockito.BDDMockito.given
import static org.mockito.Mockito.lenient
import static org.mockito.MockitoAnnotations.initMocks

@Slf4j
@TypeChecked
@TestMethodOrder(OrderAnnotation)
@DisplayName('InterServiceImpUnitTest')
@ExtendWith(MockitoExtension)
class InterServiceImpUnitTest {
    @BeforeAll
    static void init() { initMocks(this) }
    @InjectMocks
    InterServiceImp interService
    @Mock
    InterRepository repo
    TestData data = TestData.instance

    @Test
    @Order(1)
    @DisplayName('testGet_id_null')
    void testGet_id_null() {
        lenient()
                .when(repo.findById(null))
                .thenReturn(null)
        assert interService.get(null) == null
    }

    @Test
    @Order(2)
    @DisplayName('testGet_id_not_null_inter_not_exists')
    void testGet_id_not_null_inter_not_exists() {
        Long id = data.firstInter.id + 1
        given(repo.findById(id))
                .willReturn(Optional.empty())
        log.info(repo.findById(id).toString())
        assert interService.get(id) == null
    }


    @Test
    @Order(3)
    @DisplayName('testGet_inter_exists')
    void testGet_inter_exists() {
        given(repo.findById(data.firstInterDto.id))
                .willReturn(Optional
                        .ofNullable(data.firstInterDto))
        assert reflectionEquals(
                interService.get(data.firstInterDto.id),
                data.firstInterDto)
    }

    @Test
    @Order(4)
    @DisplayName('testGetFirst_return_blank_interDto')
    void testGetFirst_return_null() {
        given(repo.findByIdMin())
                .willReturn(Optional.empty())
        assert reflectionEquals(new InterDto(),
                interService.getFirst())
    }


    @Test
    @Order(5)
    @DisplayName('testGetFirst')
    void testGetFirst() {
        given(repo.findByIdMin())
                .willReturn(Optional.of(data.firstInterDto))
        assert reflectionEquals(interService.first,
                data.firstInterDto)
    }

    @Test
    @Order(6)
    @DisplayName('testGetLast_return_empty_dto')
    void testGetLast_return_empty_dto() {
        given(repo.findByIdMax())
                .willReturn(Optional.empty())
        assert reflectionEquals(new InterDto(),
                interService.getLast())
    }

    @Test
    @Order(7)
    @DisplayName('testGetLast')
    void testGetLast() {
        given(repo.findByIdMax())
                .willReturn(Optional.of(data.lastInterDto))
        assert reflectionEquals(interService.last,
                data.lastInterDto)
    }

    @Test
    @Order(8)
    @DisplayName('testGetPrevious_with_id_null')
    void testGetPrevious_with_id_null() {
        given(repo.findById(null))
                .willReturn(Optional.empty())
        assert !interService.getPrevious(null)
    }

    @Test
    @Order(9)
    @DisplayName('testGetPrevious_with_id_first')
    void testGetPrevious_with_id_first() {
        given(repo.findById(data.firstInter.id))
                .willReturn(Optional.of(data.firstInterDto))
        given(repo.findByIdMin())
                .willReturn(Optional.of(data.firstInterDto))
        assert reflectionEquals(data.firstInterDto,
                interService.getPrevious(data.firstInter.id))
    }

    @Test
    @Order(10)
    @DisplayName('testGetPrevious')
    void testGetPrevious() {
        lenient().when(repo.findById(data.interDto.id))
                .thenReturn(Optional.of(data.interDto))
        given(repo.findById(data.interDto.id - 1))
                .willReturn(Optional.of(data.prevInterDto))
        assert reflectionEquals(
                interService.getPrevious(data.interDto.id),
                data.prevInterDto)
    }

    @Test
    @Order(11)
    @DisplayName('testGetNext_with_id_null')
    void testGetNext_with_id_null() {
        given(repo.findById(null))
                .willReturn(Optional.empty())
        assert !interService.getNext(null)
    }

    @Test
    @Order(12)
    @DisplayName('testGetNext_with_id_last')
    void testGetNext_with_id_last() {
        lenient().when(repo.findById(data.inter.id))
                .thenReturn(Optional.of(data.interDto))
        given(repo.findById(data.inter.id + 1))
                .willReturn(Optional.of(data.nextInterDto))
        assert reflectionEquals(data.nextInterDto,
                interService.getNext(data.interDto.id))
    }

    @Test
    @Order(13)
    @DisplayName('test_findById_id_is_null')
    void test_findById_id_is_null() {
        assert !interService.findById(null)
    }


    @Test
    @Order(14)
    @DisplayName('test_findById_id_is_max_than_count')
    void test_findById_id_is_max_than_count() {
        Long id = 150_000_000_000
        given(repo.findById(id)).willReturn(Optional.empty())
        assert !interService.findById(id)
    }


    @Test
    @Order(15)
    @DisplayName('test_findById')
    void test_findById() {
        given(repo.findById(data.inter.id))
                .willReturn(Optional.of(data.interDto))
        assert reflectionEquals(data.interDto,
                interService.findById(data.inter.id))
    }


    @Test
    @Order(16)
    @DisplayName('testIsUniqueIndexConsistent')
    void testIsUniqueIndexConsistent_with_param_null() {
        assert !interService.isUniqueIndexConsistent(
                null, null, null)
    }


    @Test
    @Order(16)
    @DisplayName('testIsUniqueIndexConsistent')
    void testIsUniqueIndexConsistent_with_id_over_records() {
        Long id = 150_000_000_000_000L
        given(repo.findById(id)).willReturn(Optional.empty())
        assert !interService.isUniqueIndexConsistent(
                150_000_000_000_000L,
                data.inter.nd,
                data.inter.typeInter.name())
    }

    @Test
    @Order(17)
    @DisplayName('testIsUniqueNdIndexConsistent_with_existing_index')
    void testIsUniqueNdIndexConsistent_with_existing_index() {
        assert !interService.isUniqueIndexConsistent(
                data.firstInter.id,
                data.firstInter.nd,
                data.firstInter.typeInter.name())
    }


    @Test
    @Order(18)
    @DisplayName('testIsUniqueIndexConsistent')
    void testIsUniqueNdIndexConsistent_with_nd_type_not_already_exists() {
        given(repo.findById(data.firstInterDto.id))
                .willReturn(Optional.of(data.firstInterDto))
        given(repo.find(data.firstInter.nd, data.firstInter.typeInter))
                .willReturn(Optional.of(data.firstInterDto))


        log.info repo.findById(data.firstInterDto.id).toString()
        log.info repo.find(data.firstInter.nd, data.firstInter.typeInter).toString()


        assert interService.isUniqueIndexConsistent(
                data.firstInter.id,
                '0123456789',
                data.firstInterDto.typeInter)
    }


    @Test
    @Order(19)
    @DisplayName('testIsUniqueIndexAvailable_not_available')
    void testIsUniqueIndexAvailable_not_available_args_null() {
        assert !interService.isUniqueIndexAvailable(
                null, null)
    }

    @Test
    @Order(20)
    @DisplayName('testIsUniqueIndexAvailable_not_available')
    void testIsUniqueIndexAvailable_args_nd_type_not_available() {
        given(repo.find(
                data.firstInter.nd, data.firstInter.typeInter))
                .willReturn(Optional.of(data.firstInterDto))
        assert !interService.isUniqueIndexAvailable(
                data.firstInterDto.nd,
                data.firstInterDto.typeInter)
    }


    @Test
    @Order(21)
    @DisplayName('testIsUniqueIndexAvailable_args_nd_type_available')
    void testIsUniqueIndexAvailable_args_nd_type_available() {
        String nd = '0123456789'
        given(repo.find(
                nd, data.firstInter.typeInter))
                .willReturn(Optional.empty())
        assert interService.isUniqueIndexAvailable(
                nd,
                data.firstInterDto.typeInter)
    }
}
