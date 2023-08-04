package com.yc.biz;

import com.yc.bean.Account;
import com.yc.bean.OpRecord;
import com.yc.bean.OpType;
import com.yc.dao.AccountDao;
import com.yc.dao.OpRecordDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@Transactional
public class AccountBizImpl implements AccountBiz{


    @Autowired
    private AccountDao accountDao;
    @Autowired
    private OpRecordDao opRecordDao;

    @Override
    public Account openAccount(double money) {
        //开户操作 返回新的账号的id
        int accountid=this.accountDao.insert(money);
        //包装日志信息
        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(accountid);
        opRecord.setOpmoney(money);
        opRecord.setOpType(OpType.DEPOSITE);
        this.opRecordDao.insertOpRecord(opRecord);

        //返回新的账户信息
        Account a=new Account();
        a.setAccountid(accountid);
        a.setMoney(money);
        return a;
    }

    @Override
    public Account deposite(int accountId, double money) {
        return this.deposite(accountId,money,null);
    }

    @Override
    public Account deposite(int accountId, double money, Integer transferid) {
        Account a=null;
        try {
            a = this.accountDao.findById(accountId);
        }catch (RuntimeException re){
            log.error(re.getMessage());  //TODO:封装保存日志的操作
            throw new RuntimeException("查无此账户"+accountId+"无法完成此操作");
        }
        //存款时 金额累加
        a.setMoney(a.getMoney()+money);

        this.accountDao.update(accountId,a.getMoney());
        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(accountId);
        opRecord.setOpmoney(money);
        if (transferid!=null){
            opRecord.setOpType(OpType.TRANSFER);
            opRecord.setTransferid(transferid);
        }else {
            opRecord.setOpType(OpType.DEPOSITE);
        }
        this.opRecordDao.insertOpRecord(opRecord);
        return a;






    }

    @Override
    public Account withdraw(int accountId, double money) {
        return this.withdraw(accountId,money,null);
    }

    @Override
    public Account withdraw(int accountId, double money, Integer transferid) {
        Account a=null;
        try {
            a = this.accountDao.findById(accountId);
        }catch (RuntimeException re){
            log.error(re.getMessage());  //TODO:封装保存日志的操作
            throw new RuntimeException("查无此账户"+accountId+"无法完成此操作");
        }
        //存款时 金额累加
        a.setMoney(a.getMoney()+money);
        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(accountId);
        opRecord.setOpmoney(money);
        if (transferid!=null){
            opRecord.setOpType(OpType.TRANSFER);
            opRecord.setTransferid(transferid);
        }else {
            opRecord.setOpType(OpType.WITHDRAW);
        }
        this.opRecordDao.insertOpRecord(opRecord);  //先插入日志
        this.accountDao.update(accountId,a.getMoney()); //再减金额
        return a;

    }

    @Override
    public Account transfer(int accountId, double money, int toaccountId) {
        //从accountId转money到 toAccountId
        this.deposite(toaccountId,money,accountId); //收款方
        //accountid从账户中取money
        Account a=this.withdraw(accountId,money,toaccountId);
        return a;
    }

    @Override
    public Account findAccount(int accountId) {
        return this.accountDao.findById(accountId);
    }
}
