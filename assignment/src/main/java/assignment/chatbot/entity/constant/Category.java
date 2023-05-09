package assignment.chatbot.entity.constant;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Category {

	public static final Set<String> GREETING =
		new HashSet<>(List.of("너", "이름", "뭐야", "안녕", "안녕하세요", "헬로", "반갑습니다",
			"누구", "누구세요", "누구시죠", "자기소개", "인사", "시작", "무엇"));

	public static final Set<String> ASTAR =
		new HashSet<>(List.of("ASTAR", "아스타", "소개", "회사"));

	public static final Set<String> SERVICE =
		new HashSet<>(List.of("서비스", "작업", "업무", "제품", "제공", "공급", "판매", "기능", "특징", "품질",
			"도구", "기구", "솔루션", "해결책", "해결", "컨설팅", "커스터마이징", "데모", "시연", "성능", "장점", "아비카",
			"프로그램", "인터페이스", "아스타에서"));

	public static final Set<String> TECHNOLOGY =
		new HashSet<>(List.of("기술", "기술력", "기술개발", "시스템", "소프트웨어", "클라우드", "프레임워크", "언어",
			"데이터베이스", "데이터", "분석", "통계"));

	public static final Set<String> BUSINESS =
		new HashSet<>(List.of("구상", "비즈니스", "아이디어", "사업", "이익", "경영", "전략", "마케팅", "판매", "판매전략",
			"경쟁력", "성장", "성장성", "시장", "고객", "수익", "매출", "고객", "경쟁", "기회", "적용"));

	public static final Set<String> SUPPORT =
		new HashSet<>(List.of("보증", "직원", "기술지원", "업그레이드", "최적화", "고객관리", "교육", "사후관리", "A/S",
			"A/S정책", "FAQ", "지원", "유지보수", "트레이닝", "문제", "사후지원", "사후"));

	public static final Set<String> CONTACT =
		new HashSet<>(List.of("연락처", "연락", "연락하면", "연락할", "가격", "계약", "비용", "문의", "이메일", "안내",
			"이용방법", "도움", "할인", "상담", "신청", "가격책정", "가격상담", "이용", "견적", "이용문의", "이용상담",
			"견적문의", "견적상담"));
}
