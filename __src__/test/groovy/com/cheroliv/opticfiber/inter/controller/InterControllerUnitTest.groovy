package com.cheroliv.opticfiber.inter.controller

import com.cheroliv.opticfiber.TestData
import com.cheroliv.opticfiber.inter.controller.exception.*
import com.cheroliv.opticfiber.inter.domain.InterDto
import com.cheroliv.opticfiber.inter.service.InterService
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult

import static InterController.INTER_BASE_URL_REST_API
import static org.mockito.ArgumentMatchers.anyLong
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.BDDMockito.given
import static org.mockito.Mockito.*
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@Slf4j
@TypeChecked
@TestMethodOrder(OrderAnnotation)
@DisplayName("InterControllerUnitTest")
@WebMvcTest(value = InterController)
class InterControllerUnitTest {

    @Autowired
    MockMvc mockMvc
    @MockBean
    InterService interService
    TestData data = TestData.getInstance()

    @Test
    @Order(1)
    @DisplayName("test_getFirst")
    void test_getFirst() throws Exception {
        given(interService.getFirst()).willReturn(
                data.firstInterDto)
        mockMvc.perform(get(
                "${INTER_BASE_URL_REST_API}/first"))
                .andExpect(jsonPath("id")
                        .value(data.firstInterDto.id))
                .andExpect(jsonPath("nd")
                        .value(data.firstInterDto.nd))
                .andExpect(jsonPath("typeInter")
                        .value(data.firstInterDto.typeInter))
                .andExpect(status().isOk())
    }

    @Test
    @Order(2)
    @DisplayName("test_getFirst_not_found")
    void test_getFirst_not_found()
            throws FirstInterNotFoundException {
        given(interService.getFirst())
                .willThrow(FirstInterNotFoundException)
        mockMvc.perform(get(
                "${INTER_BASE_URL_REST_API}/first"))
                .andExpect(status().isNotFound())
    }


    @Test
    @Order(3)
    @DisplayName("test_getLast")
    void test_getLast() {
        given(interService.getLast())
                .willReturn(data.lastInterDto)
        mockMvc.perform(get(
                "${INTER_BASE_URL_REST_API}/last"))
                .andExpect(jsonPath("id")
                        .value(data.lastInterDto.id))
                .andExpect(jsonPath("nd")
                        .value(data.lastInterDto.nd))
                .andExpect(jsonPath("typeInter")
                        .value(data.lastInterDto.typeInter))
                .andExpect(status().isOk())
    }


    @Test
    @Order(4)
    @DisplayName("test_getLast_not_found")
    void test_getLast_not_found()
            throws LastInterNotFoundException {
        given(interService.getLast())
                .willReturn(null)
        MvcResult result = mockMvc.perform(get(
                "${INTER_BASE_URL_REST_API}/last"))
                .andExpect(status().isNotFound())
                .andReturn()
        assert result.resolvedException instanceof
                LastInterNotFoundException
    }


    @Test
    @Order(5)
    @DisplayName("test_get_with_param_id")
    void test_get_with_param_id() {
        given(interService.get(data.interDto.id))
                .willReturn(data.interDto)
        mockMvc.perform(get(
                "$INTER_BASE_URL_REST_API/{id}",
                data.interDto.id))
                .andExpect(jsonPath("id")
                        .value(data.interDto.id))
                .andExpect(jsonPath("nd")
                        .value(data.interDto.nd))
                .andExpect(jsonPath("typeInter")
                        .value(data.interDto.typeInter))
                .andExpect(status().isOk())
    }


    @Test
    @Order(6)
    @DisplayName("test_getInter_with_param_id_not_found")
    void test_get_inter_with_param_id_not_found()
            throws InterResourceNotFoundException {
        given(interService.get(data.interDto.id))
                .willReturn(null)
        MvcResult result = mockMvc.perform(get(
                "${INTER_BASE_URL_REST_API}/{id}",
                data.interDto.id))
                .andExpect(status().isNotFound())
                .andReturn()
        assert result.resolvedException instanceof
                InterResourceNotFoundException
    }

    @Test
    @Order(7)
    @DisplayName("test_getPrevious_with_param_id")
    void test_getPrevious_with_param_id() {
        given(interService.getPrevious(data.interDto.id))
                .willReturn(data.prevInterDto)
        mockMvc.perform(get(
                "${INTER_BASE_URL_REST_API}/{id}/prev",
                data.interDto.id))
                .andExpect(jsonPath("id")
                        .value(data.prevInterDto.id))
                .andExpect(jsonPath("nd")
                        .value(data.prevInterDto.nd))
                .andExpect(jsonPath("typeInter")
                        .value(data.prevInterDto.typeInter))
                .andExpect(status().isOk())
    }

    @Test
    @Order(8)
    @DisplayName("test_getPrevious_with_param_id_not_found")
    void test_getPrevious_with_param_id_not_found()
            throws PreviousInterNotFoundException {
        given(interService.getPrevious(data.interDto.id))
                .willReturn(null)
        MvcResult result = mockMvc.perform(get(
                "${INTER_BASE_URL_REST_API}/{is}/prev",
                data.interDto.id))
                .andExpect(status().isNotFound())
                .andReturn()
        assert result.resolvedException instanceof
                PreviousInterNotFoundException
    }

    @Test
    @Order(9)
    @DisplayName("test_getNext_with_param_id")
    void test_getNext_with_param_id() {
        given(interService.getNext(data.interDto.id))
                .willReturn(data.nextInterDto)
        mockMvc.perform(get(
                "${INTER_BASE_URL_REST_API}/{id}/next",
                data.interDto.id))
                .andExpect(jsonPath("id")
                        .value(data.nextInterDto.id))
                .andExpect(jsonPath("nd")
                        .value(data.nextInterDto.nd))
                .andExpect(jsonPath("typeInter")
                        .value(data.nextInterDto.typeInter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id")
                        .value(data.nextInterDto.id))
                .andExpect(jsonPath("nd")
                        .value(this.data.nextInterDto.nd))
                .andExpect(jsonPath("typeInter")
                        .value(this.data.nextInterDto.typeInter))
                .andReturn()
    }

    @Test
    @Order(10)
    @DisplayName("test_getNext_with_param_id_not_found")
    void test_getNext_with_param_id_not_found()
            throws NextInterNotFoundException {
        given(interService.getNext(data.interDto.id))
                .willReturn(null)
        MvcResult result = mockMvc.perform(get(
                "${INTER_BASE_URL_REST_API}/{id}/next",
                data.interDto.id))
                .andExpect(status().isNotFound())
                .andReturn()
        result.resolvedException instanceof
                NextInterNotFoundException
    }


    @Test
    @Order(11)
    @DisplayName("test_save")
    void test_save() {
        given(interService.save(data.newInterDto))
                .willReturn(data.expectedPersistedInterDto)
        given(this.interService.isUniqueIndexAvailable(
                anyString(), anyString())).willReturn(true)
        mockMvc.perform(post(INTER_BASE_URL_REST_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(data.jsonNewtInterDto))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(
                        APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(jsonPath("nd")
                        .value(data.newInterDto.nd))
                .andExpect(jsonPath("typeInter")
                        .value(data.newInterDto.typeInter))
                .andExpect(jsonPath("firstName")
                        .value(data.newInterDto.firstName))
                .andExpect(jsonPath("lastName")
                        .value(data.newInterDto.lastName))
                .andReturn()
    }


    @Test
    @Order(12)
    @DisplayName("test_save_id_already_exists_before_save")
    void test_save_id_already_exists_before_save()
            throws InterIdAlreadyExistsBeforeSaveException {
        given(interService.save(data.firstInterDto))
                .willReturn(null)
        MvcResult result = mockMvc.perform(post(INTER_BASE_URL_REST_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(data.jsonFirstInterDto))
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
        assert result.resolvedException instanceof
                InterIdAlreadyExistsBeforeSaveException
    }

    @Test
    @Order(13)
    @DisplayName("test_save_nd_and_type_unique_index_constraint_violation")
    void test_save_nd_and_type_unique_index_constraint_violation()
            throws InterAlreadyExistsException {
        given(interService.isUniqueIndexAvailable(
                anyString(), anyString()))
                .willReturn(false)
        MvcResult result = mockMvc.perform(post(
                INTER_BASE_URL_REST_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(data.jsonFirstInterDtoWithoutId))
                .andExpect(status().isConflict())
                .andReturn()
        assert result.resolvedException instanceof
                InterAlreadyExistsException
    }


    @Test
    @Order(14)
    @DisplayName("test_delete")
    void test_delete() {
        when(interService.findById(data.firstInterDto.id))
                .thenReturn(data.firstInterDto)
        doNothing().when(interService)
                .delete(data.firstInterDto.id)
        mockMvc.perform(delete("$INTER_BASE_URL_REST_API/{id}",
                data.firstInterDto.id))
                .andExpect(status().isNoContent())
                .andReturn()
        verify(interService, times(1))
                .findById(data.firstInterDto.getId())
        verify(interService, times(1))
                .delete(data.firstInterDto.getId())
        verifyNoMoreInteractions(interService)
    }


    @Test
    @Order(15)
    @DisplayName("test_delete_id_not_exists_before_delete")
    void test_delete_id_not_exists_before_delete()
            throws InterIdNotExistsBeforeDeleteException {
        given(interService.findById(data.firstInterDto.id + 1))
                .willReturn(null)
        def result = mockMvc.perform(delete(
                "${INTER_BASE_URL_REST_API}/{id}",
                data.firstInterDto.id + 1))
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
        assert result.resolvedException instanceof
                InterIdNotExistsBeforeDeleteException
    }


    @Test
    @Order(16)
    @DisplayName("test_update")
    void test_update() {
        given(interService.isUniqueIndexConsistent(
                anyLong(),
                anyString(),
                anyString())).willReturn(true)
        given(interService.save(data.firstInterDto))
                .willReturn(data.firstInterDto)
        mockMvc.perform(put(INTER_BASE_URL_REST_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(data.jsonFirstInterDto))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
    }

    @Test
    @Order(17)
    @DisplayName('test_update_with_inter_id_null_before_update')
    void test_update_with_inter_id_null_before_update()
            throws InterIdNullBeforeUpdateException {
        String jsonFirstInterDto =
                '{"nd":"0144639035","firstName":"John",' +
                        '"lastName":"Doe","contract":"IQ",' +
                        '"typeInter":"BAOC","dateTime":' +
                        '"2018-10-29 10:00:00"}'
        MvcResult mvcResult = mockMvc.perform(
                put(INTER_BASE_URL_REST_API)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(jsonFirstInterDto))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andReturn()
        assert mvcResult.resolvedException instanceof
                InterIdNullBeforeUpdateException
    }

    @Test
    @Order(18)
    @DisplayName('test_update_with_inter_already_exists')
    void test_update_with_inter_already_exists()
            throws InterAlreadyExistsException {
        String jsonFirstInterDto = '{"id":1, ' +
                '"nd":"0144639035","firstName":"John",' +
                '"lastName":"Doe","contract":"IQ",' +
                '"typeInter":"BAOC","dateTime":' +
                '"2018-10-29 10:00:00"}'
        given(interService.isUniqueIndexConsistent(
                anyLong(),
                anyString(),
                anyString()))
                .willReturn(false)
        MvcResult mvcResult = mockMvc.perform(
                put(INTER_BASE_URL_REST_API)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(jsonFirstInterDto))
                .andExpect(status().isConflict())
                .andDo(print())
                .andReturn()
        assert mvcResult.resolvedException instanceof
                InterAlreadyExistsException
    }

    @Test
    @Order(19)
    @DisplayName("test_update_with_patch")
    void test_update_with_patch() {
        def newFirstName = "Jane"
        def newLastName = "Doe"
        String jsonPatchFirstInterDto = "{\"id\"" +
                ":1,\"firstName\":" +
                "\"${newFirstName}\"," +
                "\"lastName\":\"${newLastName}\"}"
        def interDtoToPatch = new InterDto(
                id: 1,
                firstName: newFirstName,
                lastName: newLastName)
        def expectedResultInterDtoToPatch = new InterDto(
                id: 1,
                firstName: newFirstName,
                lastName: newLastName,
                typeInter: data.firstInterDto.typeInter,
                contract: data.firstInterDto.contract,
                dateTime: data.firstInterDto.dateTime,
                nd: data.firstInterDto.nd)
        given(interService.isUniqueIndexConsistent(
                anyLong(),
                anyString(),
                anyString())).willReturn(true)
        given(interService.saveWithPatch(interDtoToPatch))
                .willReturn(expectedResultInterDtoToPatch)
        mockMvc.perform(patch(INTER_BASE_URL_REST_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(jsonPatchFirstInterDto))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
    }

    @Test
    @Order(20)
    @DisplayName('test_update_with_patch_Inter_Id_Null_Before_Patch')
    void test_update_with_patch_Inter_Id_Null_Before_Patch()
            throws InterIdNullBeforePatchException {
        String jsonFirstInterDto =
                '{"nd":"0144639035","firstName":"John",' +
                        '"lastName":"Doe","contract":"IQ",' +
                        '"typeInter":"BAOC","dateTime":' +
                        '"2018-10-29 10:00:00"}'
        MvcResult mvcResult = mockMvc.perform(
                patch(INTER_BASE_URL_REST_API)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(jsonFirstInterDto))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andReturn()
        assert mvcResult.resolvedException instanceof
                InterIdNullBeforePatchException
    }

    @Test
    @Order(21)
    @DisplayName('test_update')
    void test_update_with_patch_inter_unique_index_not_consistent()
            throws InterAlreadyExistsException {
        String jsonFirstInterDto = '{"id":1, ' +
                '"nd":"0144639035","firstName":"John",' +
                '"lastName":"Doe","contract":"IQ",' +
                '"typeInter":"BAOC","dateTime":' +
                '"2018-10-29 10:00:00"}'
        given(interService.isUniqueIndexConsistent(
                anyLong(),
                anyString(),
                anyString()))
                .willReturn(false)
        MvcResult mvcResult = mockMvc.perform(
                patch(INTER_BASE_URL_REST_API)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(jsonFirstInterDto))
                .andExpect(status().isConflict())
                .andDo(print())
                .andReturn()
        assert mvcResult.resolvedException instanceof
                InterAlreadyExistsException

    }
}