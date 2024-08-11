package corea.auth.acceptance;

import corea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class MemberStep {
    @Autowired
    MemberRepository memberRepository;

    public void 유저네임을_통해_조회(final String username){
        assertThat(memberRepository.findByUsername(username)).isPresent();
    }
    public void 유저네임에_해당하는_유저가_있는지_검증(final String username){
        assertThat(memberRepository.findByUsername(username)).isEmpty();
    }
}
