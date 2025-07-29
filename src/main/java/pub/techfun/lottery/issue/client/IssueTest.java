package pub.techfun.lottery.issue.client;

import com.alibaba.fastjson2.JSON;
import pub.techfun.lottery.issue.Issue;

import java.util.List;

public class IssueTest {

    public static void main(String[] args) {
        List<Issue> selfIssueList = SelfIssueGenerator.generate(30, 1);
        System.out.println("issue count: " + selfIssueList.size());
        System.out.println(JSON.toJSONString(selfIssueList));

        List<Issue> cqSSCIssueList = OfficialIssueGenerator.cqSSC(1);
        System.out.println("issue count: " + cqSSCIssueList.size());
        System.out.println(JSON.toJSONString(cqSSCIssueList));

        List<Issue> qiXingCaiIssueList = OfficialIssueGenerator.qiXingCai(1);
        System.out.println("issue count: " + qiXingCaiIssueList.size());
        System.out.println(JSON.toJSONString(qiXingCaiIssueList));
    }

}
