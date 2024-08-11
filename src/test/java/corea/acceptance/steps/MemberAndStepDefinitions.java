package corea.acceptance.steps;

import corea.acceptance.CucumberClient;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberAndStepDefinitions {

    @Autowired
    CucumberClient cucumberClient;

    @Autowired
    MemberRepository memberRepository;

    @And("이미 {string}라는 유저네임을 가지는 유저가 존재하지 않는다.")
    public void 유저네임을_가지는_유저가_있는지_검증한다(final String username) {
        if (memberRepository.findByUsername(username)
                .isPresent()) {
            throw new IllegalStateException(String.format("%s에 대한 유저가 존재합니다.", username));
        }
    }

    @And("이미 {string}라는 유저네임을 가지는 유저가 존재한다.")
    public void 유저네임을_가진_유저를_생성한다(final String username) {
        final Member member = memberRepository.save(new Member(username, "thumbnialUrl", "조희선", "corea@email.com", false));
        cucumberClient.addData("MEMBER", member);
    }

    @Then("유저네임이 {string}인 유저가 조회되어야 한다.")
    public void 유저네임으로_유저를_조회한다(final String username) {
        assertThat(memberRepository.findByUsername(username)).isPresent();
    }
}
