package site.deepsleep.dyfawd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.deepsleep.dyfawd.advice.exception.security.CSensorAuthFailedException;
import site.deepsleep.dyfawd.domain.sensor.SensorInfoRepository;

@RequiredArgsConstructor
@Service
public class SensorUserDetailService implements UserDetailsService {

    private final SensorInfoRepository sensorInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String sensorPk) throws UsernameNotFoundException {
        return sensorInfoRepository.findById(Long.valueOf(sensorPk)).orElseThrow(CSensorAuthFailedException::new);
    }
}
