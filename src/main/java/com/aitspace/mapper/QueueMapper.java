package com.aitspace.mapper;

import com.aitspace.dto.request.QueueRequestDTO;
import com.aitspace.dto.response.QueueResponseDTO;
import com.aitspace.entity.*;
import com.aitspace.entity.Office;
import com.aitspace.entity.Queue;

public class QueueMapper {

    private QueueMapper() {
    }

    public static Queue toEntity(QueueRequestDTO r, Office office) {
        Queue q = new Queue();
        q.setOffice(office);
        q.setQueueName(r.getQueueName());
        q.setTotalSeats(r.getTotalSeats());
        q.setActive(true);
        return q;
    }

    public static QueueResponseDTO toResponse(Queue q) {
        QueueResponseDTO r = new QueueResponseDTO();
        r.setQueueId(q.getQueueId());
        r.setQueueName(q.getQueueName());
        r.setOfficeName(q.getOffice() != null ? q.getOffice().getOfficeName() : null);
        r.setTotalSeats(q.getTotalSeats());
        r.setActive(q.isActive());
        return r;
    }

    public static void updateEntity(Queue q, QueueRequestDTO r, Office office) {
        q.setOffice(office);
        q.setQueueName(r.getQueueName());
        q.setTotalSeats(r.getTotalSeats());
    }
}
