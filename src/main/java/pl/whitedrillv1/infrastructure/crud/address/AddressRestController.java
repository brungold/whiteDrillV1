package pl.whitedrillv1.infrastructure.crud.address;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.whitedrillv1.domain.crud.ClinicCrudFacade;

@Log4j2
@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressRestController {

    private final ClinicCrudFacade clinicCrudFacade;
}
