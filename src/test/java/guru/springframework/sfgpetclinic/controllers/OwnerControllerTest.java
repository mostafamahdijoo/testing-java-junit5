package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    @Mock
    BindingResult bindingResult;

    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    @InjectMocks
    OwnerController ownerController;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
            .willAnswer(invocation -> {
                List<Owner> ownerList = new ArrayList<>();
                String name = invocation.getArgument(0);
                if(name.equals("%Buck%")) {
                    ownerList.add(new Owner(1L, "Joe", "Buck"));
                    return ownerList;
                } else if(name.equals("%DontFindMe%")) {
                    return ownerList;
                } else if(name.equals("%FindMe%")) {
                    ownerList.add(new Owner(1L, "Joe1", "Buck1"));
                    ownerList.add(new Owner(2L, "Joe2", "Buck2"));
                    return ownerList;
                }

                throw new RuntimeException("Invalid Arguments");
            }
        );
    }

    @Test
    void processFindFormWildcardFindOne() {
        //given
        Owner owner = new Owner(1L, "Joe", "Buck");
        //List<Owner> ownerList = new ArrayList<>();
        //given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);

        //when
        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        //then
        assertThat("%Buck%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("redirect:/owners/1").isEqualToIgnoringCase(viewName);
        verifyZeroInteractions(model);
    }

    @Test
    void processFindFormWildcardNotFound() {
        //given
        Owner owner = new Owner(1L, "Joe", "DontFindMe");

        //when
        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        verifyNoMoreInteractions(ownerService);

        //then
        assertThat("%DontFindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
        verifyZeroInteractions(model);
    }

    @Test
    void processFindFormWildcardFoundMoreThanOne() {
        //given
        Owner owner = new Owner(1L, "Joe", "FindMe");
        InOrder inOrder = inOrder(ownerService, model);

        //when
        String viewName = ownerController.processFindForm(owner, bindingResult, model);

        //then
        assertThat("%FindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/ownersList").isEqualToIgnoringCase(viewName);

        //inOrder asserts
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model, times(1)).addAttribute(anyString(), anyList());
        verifyNoMoreInteractions(model);
    }

    @Test
    void processCreationFormBindingResultHasErrors() {
        //given
        Owner owner = new Owner(1L, "firstName1", "lastName1");
        given(bindingResult.hasErrors()).willReturn(true);

        //when
        String viewName = ownerController.processCreationForm(owner, bindingResult);

        //then
        assertNotNull(viewName);
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
        then(ownerService).shouldHaveZeroInteractions();
    }

    @Test
    void processCreationFormBindingResultHasNoErrors() {
        //given
        Owner owner = new Owner(5L, "firstName1", "lastName1");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);

        //when
        String viewName = ownerController.processCreationForm(owner, bindingResult);

        //then
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
        then(ownerService).should().save(any());
    }
}