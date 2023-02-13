package ProjectDoge.StudentSoup.repository.member;

import ProjectDoge.StudentSoup.dto.member.MemberFindAccountDto;
import ProjectDoge.StudentSoup.dto.member.MemberSearch;

import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.member.QMember;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import static ProjectDoge.StudentSoup.entity.file.QImageFile.imageFile;
import static ProjectDoge.StudentSoup.entity.member.QMember.member;
import static ProjectDoge.StudentSoup.entity.school.QDepartment.department;
import static ProjectDoge.StudentSoup.entity.school.QSchool.school;


@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member fullFindById(Long id){
        Member query = queryFactory.select(member)
                .from(member)
                .leftJoin(member.school, school)
                .fetchJoin()
                .leftJoin(member.department, department)
                .fetchJoin()
                .leftJoin(member.imageFile, imageFile)
                .fetchJoin()
                .where(member.memberId.eq(id))
                .fetchOne();
        return query;
    }

    @Override
    public Optional<Member> updateFindById(Long id){
        Member query = queryFactory.select(member)
                .from(member)
                .leftJoin(member.school, school)
                .fetchJoin()
                .leftJoin(member.department, department)
                .fetchJoin()
                .where(member.memberId.eq(id))
                .fetchOne();
        return Optional.ofNullable(query);
    }

    @Override
    public Optional<Member> findById(String id) {
        Member query = queryFactory.select(member)
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(query);
    }

    @Override
    public Optional<Member> findByIdAndPwd(String id, String pwd) {

        Member query = queryFactory.select(member)
                .from(member)
                .where(member.id.eq(id), member.pwd.eq(pwd))
                .fetchOne();

        return Optional.ofNullable(query);
    }

    @Override
    public Optional<MemberFindAccountDto> findByAccountUsingEmail(String email) {
        MemberFindAccountDto result = queryFactory.select(Projections.constructor(MemberFindAccountDto.class,
                        member.id,
                        member.email,
                        member.nickname))
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<MemberFindAccountDto> findByAccountUsingEmailAndId(String email, String id) {
        MemberFindAccountDto result = queryFactory.select(Projections.constructor(MemberFindAccountDto.class,
                    member.id,
                    member.email,
                    member.nickname,
                    member.pwd))
                .from(member)
                .where(member.email.eq(email), member.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Member> findByName(String name) {
        return null;
    }

    @Override
    public List<Member> findByNameAndSchool_SchoolName(String name, String schoolName) {
        return null;
    }

    @Override
    public List<Member> findByDepartment_Id(Long id) {
        JPQLQuery<Member> query = queryFactory.select(member)
                .from(member)
                .leftJoin(member.department, department)
                .fetchJoin()
                .where(department.id.eq(id));

        return query.fetch();
    }

    @Override
    public List<Member> findBySchool_SchoolId(Long id) {
        JPQLQuery<Member> query = queryFactory.select(member)
                .from(member)
                .leftJoin(member.school, school)
                .fetchJoin()
                .leftJoin(member.department, department)
                .fetchJoin()
                .where(school.id.eq(id));

        return query.fetch();
    }

    @Override

    public List<Member> search(String field,  String value) {
        List<Member> query =queryFactory
                .select(member)
                .from(member)
                .where(checkFiled(field,value))
                .fetch();
        return query;
    }

    private BooleanExpression checkFiled(String field,String value) {
        if(field.equals("id")){
            return member.id.contains(value);
        }
        return member.nickname.contains(value);
    }

    @Override
    public Member findByNickname(String nickname) {
        JPQLQuery<Member> query = queryFactory.select(member)
                .from(member)
                .where(member.nickname.eq(nickname));

        return query.fetchOne();
    }

    @Override
    public Member findByEmail(String email) {
        JPQLQuery<Member> query = queryFactory.select(member)
                .from(member)
                .where(member.email.eq(email));

        return query.fetchOne();
    }

    @Override
    public Member findByEmailAndId(String email, String id) {
        return null;
    }
}
