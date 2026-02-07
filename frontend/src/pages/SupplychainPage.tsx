import { useState } from 'react';
import { httpClient } from '../api/httpClient';

interface Order {
  id: string;
  title: string;
  approvalStatus: string;
  orderStatus: string;
}

export const SupplychainPage = () => {
  const [title, setTitle] = useState('展位搭建总包');
  const [budget, setBudget] = useState(100000);
  const [quote, setQuote] = useState(120000);
  const [requiredDate, setRequiredDate] = useState('2030-12-31');
  const [order, setOrder] = useState<Order | null>(null);
  const [riskWarnings, setRiskWarnings] = useState<string[]>([]);

  const createOrder = async () => {
    const { data } = await httpClient.post('/platform/orders', {
      exhibitorId: 'exhibitor-a',
      title,
      budgetAmount: budget,
      quoteAmount: quote,
      requiredDate
    });
    setOrder(data);
    setRiskWarnings([]);
  };

  const callAction = async (path: string, body?: object) => {
    if (!order) return;
    const { data } = await httpClient.post(`/platform/orders/${order.id}/${path}`, body ?? {});
    setOrder(data);
  };

  const queryRisk = async () => {
    if (!order) return;
    const { data } = await httpClient.get(`/platform/orders/${order.id}/risk`);
    setRiskWarnings(data.warnings ?? []);
  };

  return (
    <div className="container">
      <div className="card">
        <h1>智能会展供应链中台</h1>
        <p>订单 → 合同 → 验收 → 结算闭环 + 风险预警 + 审批流</p>
        <input className="input" value={title} onChange={(e) => setTitle(e.target.value)} placeholder="订单标题" />
        <input className="input" type="number" value={budget} onChange={(e) => setBudget(Number(e.target.value))} placeholder="预算" />
        <input className="input" type="number" value={quote} onChange={(e) => setQuote(Number(e.target.value))} placeholder="报价" />
        <input className="input" type="date" value={requiredDate} onChange={(e) => setRequiredDate(e.target.value)} />
        <button className="btn" onClick={createOrder}>创建订单</button>

        {order && (
          <div style={{ marginTop: '1rem' }}>
            <p>订单ID：{order.id}</p>
            <p>审批状态：{order.approvalStatus}</p>
            <p>流程状态：{order.orderStatus}</p>
            <div className="row" style={{ gap: '0.5rem', justifyContent: 'flex-start', flexWrap: 'wrap' }}>
              <button className="btn" onClick={() => callAction('approve', { approved: true })}>审批通过</button>
              <button className="btn" onClick={() => callAction('contract')}>合同签署</button>
              <button className="btn" onClick={() => callAction('acceptance')}>确认验收</button>
              <button className="btn" onClick={() => callAction('settlement')}>完成结算</button>
              <button className="btn" onClick={queryRisk}>风险扫描</button>
            </div>
            {riskWarnings.length > 0 && (
              <ul>
                {riskWarnings.map((warning) => <li key={warning} className="error">{warning}</li>)}
              </ul>
            )}
          </div>
        )}
      </div>
    </div>
  );
};
